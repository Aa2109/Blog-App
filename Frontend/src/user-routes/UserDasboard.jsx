import React, { useEffect, useState } from 'react'
import Base from '../components/Base'
import { Container } from 'reactstrap'
import AddPost from '../components/AddPost'
import { getCurrentUserDetail } from '../auth'
import { deletePostById, loadPostUserWise } from '../ServerServices/Post-Services'
import { toast } from 'react-toastify'
import Post from '../components/Post'
const UserDasboard = () => {

  const [user, setUser] = useState({})
  const [posts, setPosts] = useState([])

  useEffect(()=>{
    setUser(getCurrentUserDetail())
    // console.log(getCurrentUserDetail());
    loadPostData()
  },[])

  const loadPostData=()=>{
    loadPostUserWise(getCurrentUserDetail().userId).then(data => {
      // console.log(data);
      setPosts([...data])
    }).catch(error =>{
      console.log(error);
      toast.error("Error in loading user post..")
    })
  }


    function deletePost(post){
      // user here validation or confirm boxes to sure dete or anthing what u want
      // going to delete
      deletePostById(post.postId).then(data => {
        // console.log(data);
        toast.success("Post deleted");
        let newPosts = posts.filter(p=>p.postId !== post.postId)
        setPosts([...newPosts])
      }).catch(error =>{
        console.log(error);
        toast.error("Error in deleting post")
      })
    }

  return (
    <Base>

    <div>
        <Container>
          <AddPost />

          <h1 className='my-3'>Your Posts Count : ({posts.length})</h1>
          {posts && posts.map((post, index)=>{
            return(
              <Post deletePost={deletePost} key={index} post= {post} />
            )
          })}
        </Container>
    </div>
    
    </Base>
  )
}

export default UserDasboard