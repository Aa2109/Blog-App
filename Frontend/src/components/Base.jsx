import React from 'react'
import CNavbar from './CNavbar'

const Base = ({title="Welcome to my website", children}) => {
  return (
    <div >
      <CNavbar />
      {/* <h1>{title}</h1> */}
      {children}
      {/* <h1>This is footer</h1> */}
    </div>
  )
}

export default Base