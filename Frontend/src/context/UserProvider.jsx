import React, { useEffect, useState } from 'react'
import userContext from './UserContext'
import { getCurrentUserDetail, isLoggedIn } from '../auth'

const UserProvider = ({children}) => {
  const [ user,setUser] = useState({
    data:{},
    login:false
  })
  
  useEffect(()=>{
    setUser({
      data:getCurrentUserDetail(),
      login:isLoggedIn()
    })
  },[])

  return (
    <div>
      <userContext.Provider value ={{user, setUser}}>
        {children}

      </userContext.Provider>
    </div>
  )
}

export default UserProvider