
import Base from '../components/Base'
import React, { useContext, useEffect, useState } from 'react'
import { Button, Card, CardBody, CardHeader, Col, Container, Form, FormGroup, Input, Label, Row } from 'reactstrap'
import {toast} from 'react-toastify';
import { loginUser } from '../ServerServices/UserServices'
import { doLogin } from '../auth';
import { useNavigate } from 'react-router-dom';
import userContext from '../context/UserContext';


const Login = () => {
  const navigate = useNavigate()
  const userContextData = useContext(userContext);

  const [loginDetails, setLoginDetails] = useState({
    username:'',
    password:'',

  })

  // reseting the form
  const resetData =()=>{
    setLoginDetails({
      username:'',
      password:'',
    })
  };

  const handleChange = (event,field)=>{
    let actualValue = event.target.value
    setLoginDetails({
      ...loginDetails,[field]:actualValue
    })
  }

  const handleSubmit = (event)=>{
    event.preventDefault();
    console.log("logonDetails: ",loginDetails);
    //validation
    if(loginDetails.username.trim() ==='' || loginDetails.password.trim() ===''){
      toast.error("username or password is required !")
      return;
    }

    // submit the data to server to generate token
    loginUser(loginDetails).then((data)=>{
      // save data to localStorage
      doLogin(data,()=>{
        console.log("login detail is saved to localStorage");

        userContextData.setUser({
          data:data.user,
          login:true
        })
        //redirect to userDashboard
        navigate('/user/dashboard')
        
      })
      toast.success("Login success")
      
    }).catch(error=>{
      console.log(error);
      if (error.response && (error.response.status === 400 || error.response.status === 404)) {
        toast.error(error.response.data.message);
      } else {
        toast.error('Something went wrong from the server !!');
      }
    });
  }



  return (
    <Base>
    <div>
    <Container>

    <Row className='mt-4'>
      <Col sm={{size:6, offset:3}}>

      <Card color='dark' inverse>
      <CardHeader>
        <h3 className='text-center'>Login here !!</h3>
      </CardHeader>
      <CardBody>
          {/* Creating form */}
          <Form onSubmit={handleSubmit}>
           
            {/* Email Field */}
            <FormGroup>
              <Label for='email'>Enter email</Label>
              <Input type='email' placeholder='email' id='email'
              value={loginDetails.username}
              onChange={(e)=>handleChange(e,'username')}
              />
            </FormGroup>
            {/* Password Field */}
            <FormGroup>
              <Label for='password'>Enter password</Label>
              <Input type='password' placeholder='password' id='password'
               value={loginDetails.password}
               onChange={(e)=>handleChange(e,'password')}
              />
            </FormGroup>

            <Container className='text-center'>
              <Button outline color='light'>Login</Button>
              <Button onClick={resetData} color='secondary' outline type='reset' className='ms-2' >Reset</Button>
            </Container>

          </Form>
      </CardBody>
    </Card>

      </Col>
    </Row>

    </Container>
    </div>
    </Base>
  )
}

export default Login