import React, { useEffect, useState} from 'react';
import Base from './Base';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Card, CardBody, CardText, Col, Container, Input, Row } from 'reactstrap';
import { createComment, loadPost } from '../ServerServices/Post-Services';
import { toast } from 'react-toastify';
import { BASE_URL } from '../ServerServices/helper';
import { isLoggedIn } from '../auth';

const PostPage = () => {
  const { postId } = useParams();
  const [post, setPost] = useState(null);
  const [comment, setComment] = useState({ commentContent: '' });
  const navigate = useNavigate()

  useEffect(() => {
    loadPost(postId)
      .then(data => {
        console.log(data);
        setPost(data);
      })
      .catch(error => {
        console.log(error);
        toast.error("Error in loading post");
      });
  }, []);

  const printDate = (numbers) => {
    return new Date(numbers).toLocaleDateString();
  };

  const submitComment = () => {

    if (!isLoggedIn()) {
      toast.error('You need to be logged in first!!');
      setTimeout(() => {
        navigate('/login') // Redirect to the login component after 3 seconds
      }, 3000);
      return;
    }


    if (comment.commentContent.trim() === '') {
      toast.error("Enter a comment..");
      return;
    }

    createComment(comment, post?.postId)
      .then(data => {
        // console.log(data);
        toast.success("Comment added..");
        // Reload post data to fetch updated comments
        loadPost(postId)
          .then(newPostData => {
            setPost(newPostData);
          })
          .catch(error => {
            console.log("post from PostPage: ",error);
            
            toast.error("Error in loading post");
          });
          setComment({
            commentContent:''
          })
      })
      .catch(error => {
        console.log(error);
        toast.error("Error adding comment");
      });
  };

  return (
    <div>
      <Base>
        <Container className='mt-4'>
          <Link to={'/'}>Home</Link>/{post && <Link to={''}>{post.postTitle}</Link>}
          <Row>
            <Col md={{ size: 12 }}>
              <Card className='shadow-sm mt-3'>
                <CardBody>
                  <CardText>Posted By <b>{post?.user?.name}</b> on <b>{printDate(post?.postDate)}</b></CardText>
                  <CardText className='text-muted'>{post?.category.categoryName}</CardText>
     
                  <CardText className='mt-3'>
                    <h3>{post?.postTitle}</h3>
                    <div className="image-container mt-3 shadow" style={{ maxWidth: '50%', maxHeight: '50%' }}>
                      <img src={BASE_URL + '/api/post/image/' + post?.postImage} alt="" style={{ maxWidth: '100%', maxHeight: '100%', objectFit: 'contain' }} />
                    </div>
                    <CardText className='mt-5' dangerouslySetInnerHTML={{ __html: post?.postContent }}></CardText>
                  </CardText>
                </CardBody>
              </Card>
            </Col>
          </Row>

          {/* comments section */}
          <Row className='my-4'>
            <Col md={{ size: 9, offset: 1 }}>
              {post && post.comments && (
                <>
                  <h3>comments({post.comments.length})</h3>
                  {post.comments.map((c, index) => (
                    <Card className='mt-2' key={index}>
                      <CardBody>
                        {c.user && <CardText>{c.user.name}</CardText>}
                        <CardText>{c.commentContent}</CardText>
                      </CardBody>
                    </Card>
                  ))}
                </>
              )}

              <Card className='mt-2'>
                <CardBody>
                  <Input
                    type='textarea'
                    placeholder='Comment here...'
                    value={comment.commentContent}
                    onChange={(event) => setComment({ commentContent: event.target.value })}
                  />
                  <Button onClick={submitComment} className='mt-2' color='secondary'>Submit</Button>
                </CardBody>
              </Card>
            </Col>
          </Row>
        </Container>
      </Base>
    </div>
  );
};

export default PostPage;
