import { myAxios, privateAxios } from "./helper"

// create post
export const doCreatePost=async (postData)=>{
  // console.log(postData);
  const url = `/api/user/${postData.userId}/category/${postData.categoryId}/create`;
  // console.log("Request URL:", url); // Log the URL before making the request
  
  const response = await privateAxios.post(url, postData);
  return response.data;
}

// getAll Post
export const loadAllPost= async (pageNumber, pageSize)=>{
  
let url=`/api/post/pagination/posts?pageNumber=${pageNumber}&pageSize=${pageSize}&sortBy=postDate&sortDir=desc`; 	
// console.log("Request URL:", url);  
// const response = await myAxios.get(url);
// return response.data;
  return myAxios.get(url).then((response) => response.data)

}
// load Post by given id
export const loadPost=(postId)=>{
  
let url=`/api/post/`+postId; 	
// console.log("Request URL:", url);
// const response = myAxios.get(url);
// return response.data;
  return myAxios.get(url).then((response=> response.data))

}

// comment
export const createComment = async (comment, postId)=>{
  const url = `/api/post/${postId}/create/comments`;
  // console.log("request ur: ",url);
  const response = await privateAxios.post(url, comment);
  return response.data;
}

//upload post banner image
export const uploadPostImg =async (file, postId)=>{
  let formData = new FormData();
  const url = `/api/post/image/upload/${postId}`
  formData.append("file", file)
  const response = await privateAxios.post(url, formData, {
    'Content-Type': 'mutipart/form-data'
  });
  return response.data;
}

//get category wise post
export const loadPostCategorywise = async (categoryId)=>{
const url = `/api/category/${categoryId}/posts`;
  try {
    const resp = await privateAxios.get(url);
    return resp.data;
  } catch (error) {
    console.error("Error loading posts:", error);
    throw error;
  }
  
}

// get post by user 
export const loadPostUserWise = async(userId)=>{
  const url = `/api/user/${userId}/posts`;
  try {
    const resp = await privateAxios.get(url);
    return resp.data;
  } catch (error) {
    console.error("Error loading posts:", error);
    throw error;
  }
}

// Delete post 
export async function deletePostById(postId){
  console.log("inside delete service post");
  const url = `/api/delete/post/${postId}`;
  try {
    const resp = await privateAxios.delete(url);
    return resp.data;
  } catch (error) {
    console.error("Error loading posts:", error);
    throw error;
  }
}

export async function updatePostService(post, postId){
  // console.log("inside update service post: ", post);
  const url = `/api/update/post/${postId}`;
  try {
    const resp = await privateAxios.put(url, post); // Include 'post' data in the request
    return resp.data;
  } catch (error) {
    console.error("Error updating post:", error);
    throw error;
  }
}
