import React, { useEffect, useState } from 'react';
import { deletePostById, loadAllPost } from '../ServerServices/Post-Services';
import { Col, Container, Pagination, PaginationItem, PaginationLink, Row } from 'reactstrap';
import Post from './Post';
import { toast } from 'react-toastify';
import InfiniteScroll from 'react-infinite-scroll-component';

const NewFeed = () => {
  const [postContent, setPostContent] = useState(
    { 
      content: [], 
      pageNumber: '', 
      totalPages: '', 
      totalElements: '', 
      pageSize:'',
      lastPage:'',
      pageNumber:','
     }
  );
  const [currentPage, setCurrentPage] = useState(0);

  useEffect(() => {
     changePage(currentPage)
  }, [currentPage]); 


  const changePage = (pageNumber = 0, pageSize = 4) => {

    if(pageNumber > postContent?.pageNumber && postContent.lastPage){
      return;
    }
    if(pageNumber < postContent?.pageNumber && postContent.pageNumber === 0){
      return;
    }
    loadAllPost(pageNumber, pageSize)
      .then((data) => {
        // console.log(data);
        //setPostContent(data);
        setPostContent((prevContent) => ({
          ...data,
          content: [...prevContent.content, ...data.content], // Append new posts to existing posts
        }));
        // window.scrollTo(0,0);
        // console.log(data.totalPages);  
    // console.log(data.pageNumber);

        // window.scroll(0,0)
      })
      .catch((error) => {
        toast.error('Error in loading posts!');
        console.log(error);
      });
  };


  function deletePost(post){
    // user here validation or confirm boxes to sure dete or anthing what u want
    // going to delete
    deletePostById(post.postId).then(data => {
      // console.log(data);
      toast.success("Post deleted");
      let newPostContent = postContent.content.filter(p=>p.postId != post.postId)
      setPostContent({...postContent, content :newPostContent})
      
    }).catch(error =>{
      console.log(error);
      toast.error("Error in deleting post")
    })
  }

  // const fetchNextPage=()=>{
  //   console.log("page changed");
  //   setCurrentPage(currentPage+1)
  // }

  return (
    <div className="container-fluid">
      <Row>
        <Col md={{ size: 12, 
          // offset: 1 
          }}>
          <h1>Blogs Count: ({postContent?.totalElements})</h1>

         <InfiniteScroll

          dataLength={postContent?.content.length || 0}
          next={() => changePage(postContent.pageNumber + 1)}
          hasMore={!postContent?.lastPage}
          loader={<h4>Loading...</h4>}
          endMessage={<p>No more posts to load</p>}
          scrollableTarget="scrollableDiv"
        >

         {postContent?.content.map((post) => (
            <Post deletePost={deletePost} post={post} key={post.postId}/>
          ))}

         </InfiniteScroll>

        {/* <Container className="mt-3">
          <Pagination size='lg'>
            <PaginationItem onClick={()=>changePage(postContent?.pageNumber - 1)} disabled={postContent?.pageNumber === 0}>
              <PaginationLink previous>previous</PaginationLink>
            </PaginationItem>

            {postContent?.totalPages !== undefined && [...Array(postContent?.totalPages + 1)].map((_, index) => (
              <PaginationItem onClick={() => changePage(index)} key={index} active={index === postContent?.pageNumber}>
                <PaginationLink>{index + 1}</PaginationLink>
              </PaginationItem>
            ))}

            <PaginationItem onClick={()=>changePage(postContent?.pageNumber + 1)} disabled={postContent?.pageNumber === postContent?.totalPages}>
              <PaginationLink next> next</PaginationLink>
            </PaginationItem>
          </Pagination>
        </Container> */}



        </Col>
      </Row>
    </div>
  );
};

export default NewFeed;
