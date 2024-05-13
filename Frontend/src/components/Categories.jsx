import React, { useEffect, useState } from 'react'
import Base from './Base'
import { useParams } from 'react-router-dom'
import CategorySideMenu from './CategorySideMenu';
import { Row,Col,Container } from 'reactstrap';
import { deletePostById, loadPostCategorywise } from '../ServerServices/Post-Services';
import { toast } from 'react-toastify';
import Post from './Post';

const Category = () => {

  const [posts, setPosts] = useState([])
  const {categoryId} = useParams();
  useEffect(()=>{ 
    // console.log(categoryId);
    loadPostCategorywise(categoryId).then(data=>{
      setPosts([...data])
    })
    .catch((error) => {
      console.log('Error:', error);
      // if (error.response) {
      //   console.log('Response:', error.response); // Log the error response
      // }
      toast.error('Error in loading post by category');
    });
  },[categoryId])

  function deletePost(post){
    // user here validation or confirm boxes to sure dete or anthing what u want
    // going to delete
    deletePostById(post.postId).then(data => {
      // console.log(data);
      toast.success("Post deleted");
      let newPosts = posts.filter(p=>p.postId != post.postId)
      setPosts([...newPosts])
    }).catch(error =>{
      console.log(error);
      toast.error("Error in deleting post")
    })
  }


  return (
    <Base>
    <div>

    <Container className='mt-6'>
      <Row>

        <Col md={2} className='pt-5'>
        <CategorySideMenu />
        </Col>

        <Col md={10}>

          <h1>Blogs count ({posts.length}) </h1>
          {
            posts && posts.map((post, index)=>{
              return(
                <Post deletePost={deletePost} key={index} post={post}/>
              )
            })
          }
          {posts.length <=0 ? <h1>No Posts in this category is available !!</h1> : ''}
        </Col>

      </Row>
    </Container>

    </div>
    </Base>
  )
}

export default Category