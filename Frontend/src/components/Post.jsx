import React, { useContext, useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { Button, Card, CardBody, CardText } from 'reactstrap'
import { getCurrentUserDetail, isLoggedIn } from '../auth'
import userContext from '../context/UserContext'
import 'jodit/es2021/jodit.min.css';
import { Jodit } from 'jodit';

const Post = ({post = "This is default post title", postContent="This is defult post content", deletePost}) => {

  const userContextData = useContext(userContext)
  const [user, setUser]=useState(null)
  const [login, setLogin]=useState(null)

  useEffect(()=>{
    setUser(getCurrentUserDetail())
    setLogin(isLoggedIn())
  },[])
  return (
    <div>
      <Card className='shadow-sm mt-3'>
        <CardBody>
          <h1>{post.postTitle}</h1>
          <CardText dangerouslySetInnerHTML={{__html:post.postContent.substring(0,60)+'...'}}>
          </CardText>
          <div>
            <Link className='btn btn-primary' to={'/posts/'+post?.postId}>read more</Link>

           {/* {user && (login ? (user.userId == post.user.userId ? <Button onClick={()=>deletePost(post)} className='ms-3' color='danger'>Delete</Button>  :'') :'')} */}
           {userContextData.user.login && (login ? ( user?.userId === post.user?.userId ? <Button onClick={()=>deletePost(post)} className='ms-3' color='danger'>Delete</Button>  :'') :'')}
           {userContextData.user.login && (login ? ( user?.userId === post.user?.userId ? <Button tag={Link} to={`/user/update-blog/${post.postId}`} className='ms-3' color='warning'>update</Button>  :'') :'')}

          </div>
        </CardBody>
      </Card>
    </div>
  )
}

export default Post