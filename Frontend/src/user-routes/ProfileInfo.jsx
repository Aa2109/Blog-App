import React, { useContext, useEffect, useState } from 'react'
import Base from '../components/Base'
import userContext from '../context/UserContext'
import { useParams } from 'react-router-dom'
import { getUser, updateUserService } from '../ServerServices/UserServices'
import { toast } from 'react-toastify'
import { Button, Card, CardBody, CardFooter, Col, Container, Input, Row, Table } from 'reactstrap'
import ViewUserProfile from '../components/ViewUserProfile'

const ProfileInfo = () => {
  
  const object = useContext(userContext)

 const userId =  useParams();
 const [user,setUser]= useState(null)
 const [updateFlag, setUpdateFlag] = useState(false)

 useEffect(()=>{
  getUser(userId).then(data=>{
    // console.log(data);
    setUser({...data})
  }).catch(error=>{
    console.log(error);
    toast.error("Error in loading user!!")
  })
 },[])

 const toggleUpdateFlag = (value) => {
  setUpdateFlag(value)
}

//show update profile
const showUpdateProfile = () => {
  toggleUpdateFlag(true)
}

//show view profile
const viewUpdateProflie = () => {
  toggleUpdateFlag(false)
}


/*  view user profile */
const userView = () => {
  return (

    <ViewUserProfile updateProfileClick={showUpdateProfile} user={user} />

  )
}



const viewUserProfile = () => {
  return (

    <div>
      {user ? userView() : 'Loading user Data...'}
    </div>

  )
}

// END view user Profile

//update user profile]
const updateUserProfile = () => {
  const updateUser = (event)=>{
    event.preventDefault();
    // console.log(user);
    updateUserService(user, user.userId) // Pass user directly instead of spreading
    .then(response=>{
      // console.log(response);
      toast.success("user updated !!")
      // navigate('/')
    }).catch(error=>{
      console.log(error);
      toast.error("Error in updating user")
    })
  }

  
  return (
    <div>
      <Card className='mt-2 border-0 rounded-0 shadow-sm'>
        <CardBody>
          <h3 className='text-uppercase'>user Information</h3>

          <Container className='text-center'>
            <img style={{ maxWidth: '200px', maxHeight: '200px' }} src={user.image ? user.image : 'https://cdn.dribbble.com/users/6142/screenshots/5679189/media/1b96ad1f07feee81fa83c877a1e350ce.png?compress=1&resize=400x300&vertical=top'} alt="user profile picture" className='img-fluid  rounded-circle' />
          </Container>
          {/* Add a <form> element around your table content */}
          <form onSubmit={updateUser}>
            <Table responsive striped hover bordered={true} className='text-center mt-5'>
              <tbody>
                <tr>
                  <td >
                    USER ID
                  </td>
                  <td>
                    {user.userId}
                  </td>
                </tr>
                <tr>
                  <td >
                    USER NAME
                  </td>
                  <td>
                    <Input type='text' value={user.name} onChange={(e) => setUser({ ...user, name: e.target.value })} />
                  </td>
                </tr>
                <tr>
                  <td >
                    USER EMAIL
                  </td>
                  <td>
                    {user.email}
                  </td>
                </tr>
                <tr>
                  <td >
                    ABOUT
                  </td>
                  <td>
                    <Input type='textarea' value={user.about} onChange={(e) => setUser({ ...user, about: e.target.value })} />
                  </td>

                </tr>
                <tr>
                  <td>
                    ROLE
                  </td>
                  <td>
                    {user.roles.map((role) => {
                      return (
                        <div key={role.roleId}>{role.roleName}</div>
                      )
                    })}
                  </td>
                </tr>
              </tbody>
            </Table>
            <CardFooter className='text-center'>
              <Button color='success' type="submit">Update Profile</Button> {/* Add type="submit" to trigger form submission */}
            </CardFooter>
          </form>
        </CardBody>
      </Card>
    </div>
  )
}



  return (
    <Base>
    <Row>
        <Col md={{ size: 6, offset: 3 }}>

          <Container>
            {updateFlag ? updateUserProfile() : viewUserProfile()}
          </Container>
        </Col>
      </Row>

    </Base>
  )
}

export default ProfileInfo