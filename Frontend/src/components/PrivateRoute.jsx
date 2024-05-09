import React from 'react'
import { Navigate, Outlet } from 'react-router-dom'
import { isLoggedIn } from '../auth';

const PrivateRoute = () => {
  return isLoggedIn() ? <Outlet /> : <Navigate to={'/login'} />

// if(isLoggedIn()){
//   console.log(isLoggedIn());
// return <Outlet/>
// }
// else{
//   console.log(isLoggedIn());
// return <Navigate to={'/login'}/>;
// }
 }

export default PrivateRoute