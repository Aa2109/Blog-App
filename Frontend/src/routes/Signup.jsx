import React, { useEffect, useState } from 'react'
import Base from '../components/Base'
import { Button, Card, CardBody, CardHeader, Col, Container, Form, FormFeedback, FormGroup, Input, Label, Row } from 'reactstrap'
import { signUp } from '../ServerServices/UserServices'
import {toast} from 'react-toastify';
import { useNavigate } from 'react-router-dom';

const Signup = () => {

  const navigate = useNavigate();
    const [data, setData] =  useState({
      name :'',
      email:'',
      password:'',
      about:'',

    })

    // reseting the form
    const resetData =()=>{
      setData({
        name :'',
        email:'',
        password:'',
        about:'',
      })
    };

    // submite the form
    const submitForm=(event)=>{
      event.preventDefault()

      // if(error.isError){
      //   toast.error("invalid form data !!");
      //   setError({...error, isError:false})
      //   return;
      // }
      // console.log(data);
      // valid data

      // call server apis
      signUp(data).then((resp)=>{
        // console.log(resp);
        // console.log('success log');
        toast.success("User registerd successfully, name is: "+resp.email);
        navigate('/user/dashboard')
        setData({
          name :'',
          email:'',
          password:'',
          about:'',
        });

      }).catch((error)=>{
        console.log(">>>>>>>>>>>",error);
        setError({
          errors:error,
          isError:true,
        })
        // toast.error("Something went wrong")
      })
    }

    useEffect(()=>{
      // console.log(data);
    });

    const [error, setError] = useState({
      errors:{},
      isError:false,
    });

    // handle onChange
    const handleChange=(e, property)=>{
      //dyanamic setting the values
        setData({...data, [property]:e.target.value})
    }

  return (
    <Base>
    <div>
    <Container>

    <Row className='mt-4'>
      <Col sm={{size:6, offset:3}}>

      <Card color='dark' inverse>
      <CardHeader>
        <h3 className='text-center'>Register here</h3>
      </CardHeader>
      <CardBody>
          {/* Creating form */}
          <Form onSubmit={submitForm}>

            {/* Name Field */}
            <FormGroup>
              <Label for='name'>Enter name</Label>
              <Input type='text' placeholder='name' id='name'
              onChange={(e)=>handleChange(e, 'name')} value={data.name}
              invalid={error.errors?.response?.data?.name ? true:false} />

              <FormFeedback>
                {error.errors?.response?.data?.name}
              </FormFeedback>
            </FormGroup>

            {/* Email Field */}
            <FormGroup>
              <Label for='email'>Enter email</Label>
              <Input type='email' placeholder='email' id='email'
              onChange={(e)=>handleChange(e, 'email')} value={data.email} 
              invalid={error.errors?.response?.data?.email ? true:false} />

              <FormFeedback>
                {error.errors?.response?.data?.email}
              </FormFeedback>
            </FormGroup>

            {/* Password Field */}
            <FormGroup>
              <Label for='password'>Enter password</Label>
              <Input type='password' placeholder='password' id='password'
              onChange={(e)=>handleChange(e, 'password')} value={data.password}
              invalid={error.errors?.response?.data?.password ? true:false} />

              <FormFeedback>
                {error.errors?.response?.data?.password}
              </FormFeedback>
            </FormGroup>

            {/* About Field */}
            <FormGroup>
              <Label for='about'>Enter About</Label>
              <Input type='textarea' placeholder='about' id='about' style={{height:"200px"}}
              onChange={(e)=>handleChange(e, 'about')} value={data.about}
              invalid={error.errors?.response?.data?.about ? true:false} />

              <FormFeedback>
                {error.errors?.response?.data?.about}
              </FormFeedback>
            </FormGroup>

            <Container className='text-center'>
              <Button outline color='light'>Register</Button>
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
  );
}

export default Signup