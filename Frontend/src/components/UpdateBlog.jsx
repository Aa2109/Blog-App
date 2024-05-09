import React, { useContext, useEffect, useState,editRef, useRef } from 'react'
import Base from './Base'
import { useNavigate, useParams } from 'react-router-dom'
import userContext from '../context/UserContext';
import { loadPost, updatePostService } from '../ServerServices/Post-Services';
import { toast } from 'react-toastify';
import { Button, Container, Input, Label,Card,CardBody,CardText, Form } from 'reactstrap';
import { loadAllCategory } from '../ServerServices/Category-Services';
import JoditEditor from 'jodit-react';


const UpdateBlog = () => {
  // const [content, setContent] = useState('');
  const editorRef = useRef(null);

  const {blogId}= useParams();
  const object= useContext(userContext)
  const navigate = useNavigate();
  const [post, setPost] = useState(null)
  const [user, setUser] = useState(null)
  const [categories, setCategories] = useState(null)

    useEffect(()=>{
   
      loadAllCategory().then((data)=>{
        setCategories(data)
      }).catch(error=>{
        // console.log(error);
      })


      loadPost(blogId).then(data=>{
        // console.log(data);
        setPost({...data, categoryId:data.category.categoryId});
      }).catch(error=>{
        console.log(error);
        toast.error("Error in loading the blog..")
      })
    },[])

    useEffect(()=>{
     if(post){
      if(post?.user?.userId !== object.user?.data.userId){
        toast.error("This is not your post !!")
        navigate('/')
      }
     }
    }, [post])

    
    const handleChange = (event, filedName)=>{
      setPost({
        ...post,
        [filedName]:event.target.value
      })
    }

    const updatePost = (event)=>{
      event.preventDefault()
      // console.log(post);
      updatePostService({...post, category:{categoryId:post.categoryId}},post.postId)
      .then(response=>{
        // console.log(response);
        toast.success("Post updated successfully")
        navigate('/')
      }).catch(error=>{
        console.log(error);
        toast.error("Error in updating post")
      })
    }


    const updateHtml=()=>{
      return(

        <div className='wrapper'>
          {/* {JSON.stringify(post)} */}
        <Card className='shadow-sm mt-2' color='dark' inverse>
          <CardBody>
            <h3 className='text-center'>Update your post</h3>
            <Form onSubmit={updatePost}>
              <div className='my-3'>
                <Label for='title'>Post title</Label>
                <Input type='text' id='title' placeholder='Enter here' name='postTitle' value={post.postTitle}
                onChange={(event)=>handleChange(event,"postTitle")} 
                />
              </div>
  
               {/* Post content field */}
            <div className='my-3'>
              <JoditEditor 
              ref={editorRef}
              value={post.postContent}
              onChange={newContent => setPost({ ...post, postContent: newContent })}
            />
            </div>
              


  
              {/* File field */}
              <div>
                <Label for='image'>Post image</Label>
                <Input onChange={''} id='image' type='file' />
              </div>
  

              {/* Post category field */}
              <div className='my-3'>
                <Label for='category'>Post category</Label>
                <Input type='select' id='category' name='categoryId' 
                onChange={(event) => setPost({ ...post, categoryId: event.target.value })}
                value={post.categoryId}
                >
                  <option value={0}>-- Select --</option>
                  {categories?.map((category) => (
                    <option value={category?.categoryId} key={category?.categoryId}>
                      {category?.categoryName}
                    </option>
                  ))}
                </Input>
              </div>
  
              <Container className='text-center'>
                <Button type='submit' color='primary'>Update Post</Button>
                <Button color='danger' className='ms-2'>Reset Post</Button>
              </Container>
            </Form>
          </CardBody>
        </Card>
      </div>
       

      )
    }

  
  return (
    <Base>
    <Container>
    <div>
    {post && updateHtml()}
{/* {blogId} */}


    </div>

    </Container>
    </Base>
  )
}

export default UpdateBlog