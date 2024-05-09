import React from 'react'
import Base from '../components/Base'
import userContext from '../context/UserContext'

const About = () => {
  return (
    <userContext.Consumer>
      {(object) => (
        <Base>
          <div>
            <h1>This is the About page</h1>
            <p>We are building a blog app</p>
            {/* {console.log(object)} */}
            <h1>Welcome: {object.user?.login && object.user?.data?.name}</h1>
          </div>
        </Base>
      )}
    </userContext.Consumer>
  )
}

export default About
