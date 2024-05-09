import privateAxios, { myAxios } from "./helper.jsx";

export const signUp= async (user)=>{
  const repsonse = await myAxios.post('/auth/register', user);
  return repsonse.data;
}

export const loginUser=async (loginDetail)=>{
  const Response = await myAxios.post('/auth/login', loginDetail);
  return Response.data;
}

export const getUser = async (userIdObject)=>{
  const userId = userIdObject.userId;
  const url = `/api/users/get/${userId}`
  try {
    const resp = await privateAxios.get(url); 
    return resp.data;
  } catch (error) {
    console.error("Error updating post:", error);
    throw error;
  }
}

export const updateUserService = async(user, userId)=>{
  const url = `/api/users/update/${userId}`
  // console.log("user: ",user,"   userId: ",userId,"    url:",url);
  const resp = await privateAxios.put(url,user)
  return resp.data;
}

