import React, { useEffect, useRef, useState } from 'react';
import { Button, Card, CardBody, Container, Form, Input, Label } from 'reactstrap';
import { loadAllCategory } from '../ServerServices/Category-Services';

import { doCreatePost, uploadPostImg } from '../ServerServices/Post-Services';
import { getCurrentUserDetail } from '../auth';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import JoditEditor from 'jodit-react';



const AddPost = () => {


  const editorRef = useRef(null);
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);
  const [user, setUser] = useState(undefined);
  const [image, setImage] = useState(null);
  const [post, setPost] = useState({
    postTitle: '',
    postContent: '',
    categoryId: '',
  });

  useEffect(() => {
    setUser(getCurrentUserDetail());
    loadAllCategory()
      .then((data) => {
        setCategories(data);
      })
      .catch((error) => {
        console.log(error);
      });

  }, []);

  // field chnaged
  const fieldChanged = (event) => {
    console.log(event);
    setPost({ ...post, [event.target.name]: event.target.value });
  };

  const contentFieldChanaged = (data) => {
    setPost({ ...post, 'postContent': data })
}
//create post function
  const createPost = (event) => {
    event.preventDefault();

    // Validate post content and category
    if (post?.postTitle.trim() === '' || post?.postContent.trim() === '') {
      toast.error("Post title or content can't be empty!");
      return;
    }
    if (post?.categoryId === '') {
      toast.error('Select a category!');
      return;
    }

    post['userId'] = user?.userId;
    doCreatePost(post)
      .then((data) => {
        uploadPostImg(image, data.postId)
          .then(() => {
            toast.success('image uploaded !!');
          })
          .catch((error) => {
            toast.error('Error uploading post image.');
            console.log(error);
          });

          toast.success('Post created !!');
          setPost({
            postTitle: '',
            postContent: '',
            categoryId: '',
        })
          navigate('/');
      })
      .catch((error) => {
        toast.error('Error creating post.');
        console.log(error);
      });
  };

  // Handle file change event
  const fileChange = (event) => {
    setImage(event.target.files[0]);
  };

  return (
    <div className='wrapper'>
      <Card className='shadow-sm mt-2' color='dark' inverse>
        <CardBody>
          <h3 className='text-center'>What's on your mind?</h3>
          <Form onSubmit={createPost}>
            <div className='my-3'>
              <Label for='title'>Post title</Label>
              <Input type='text' id='title' placeholder='Enter here' name='postTitle' onChange={fieldChanged} />
            </div>


            {/* Post content field */}
            <div className='my-3'>
              <JoditEditor 
              ref={editorRef}
              value={post.postContent}
              onChange={(newContent) => contentFieldChanaged(newContent)}
            />
            </div>
            
            {/* File field */}
            <div>
              <Label for='image'>Post image</Label>
              <Input onChange={fileChange} id='image' type='file' />
            </div>

            {/* Post category field */}
            <div className='my-3'>
              <Label for='category'>Post category</Label>
              <Input type='select' id='category' name='categoryId' onChange={fieldChanged} defaultValue={0}>
                <option value={0}>-- Select --</option>
                {categories?.map((category) => (
                  <option value={category?.categoryId} key={category?.categoryId}>
                    {category?.categoryName}
                  </option>
                ))}
              </Input>
            </div>

            <Container className='text-center'>
              <Button type='submit' color='primary'>Create Post</Button>
              <Button color='danger' className='ms-2'>Reset Post</Button>
            </Container>
          </Form>
        </CardBody>
      </Card>
    </div>
  );
};

export default AddPost;
