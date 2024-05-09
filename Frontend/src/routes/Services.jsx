import React from 'react'
import Base from '../components/Base'
import userContext from '../context/UserContext'

const Services = () => {
  return (
   <userContext.Consumer>
    {
      (user)=>(
        <Base>
        <div>
          <h1>This is service page</h1>
          <h1>Welcome: {user.user?.login && user.user?.data.name}</h1>
        </div>
        </Base>
      )
    }
   </userContext.Consumer>
  )
}

export default Services