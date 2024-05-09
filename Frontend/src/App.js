
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';


import About from './routes/About';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './routes/Home';
import Login from './routes/Login';
import Signup from './routes/Signup';
import Contact from './routes/Contact';
import Services from './routes/Services';
import { ToastContainer } from 'react-toastify';
import UserDasboard from './user-routes/UserDasboard';
import PrivateRoute from './components/PrivateRoute';
import ProfileInfo from './user-routes/ProfileInfo';
import PostPage from './components/PostPage';
import UserProvider from './context/UserProvider';
import Categories from './components/Categories';
import UpdateBlog from './components/UpdateBlog';


function App() {
  return (
    <UserProvider>
   <BrowserRouter >
  <ToastContainer position='bottom-center'/>
   <Routes>
      
        <Route path ="/" element={<Home />} />
        <Route path ="/login" element={<Login />} />
        <Route path ="/about" element={<About />} />
        <Route path ="/signup" element={<Signup />} />
        <Route path="/contact" element={<Contact/>}/>
        <Route path="/services" element={<Services/>}/>
        <Route path="/posts/:postId" element={<PostPage/>}/>
        <Route path="/category/:categoryId" element={<Categories/>}/>

        <Route path='/user' element={<PrivateRoute/>}>
        <Route path="dashboard" element={<UserDasboard/>}/>
        <Route path="profile-info/:userId" element={<ProfileInfo/>}/>
        <Route path="update-blog/:blogId" element={<UpdateBlog/>}/>
        </Route>

   </Routes >
   </BrowserRouter>
   </UserProvider>
  );
}

export default App;
