import React, { useContext, useEffect, useState } from 'react'
import  { NavLink as ReactLink, useNavigate} from 'react-router-dom'
import { isLoggedIn, getCurrentUserDetail, doLogout} from '../auth';
import { Collapse, Navbar, NavbarToggler, NavbarBrand, Nav, NavItem, NavLink, UncontrolledDropdown, DropdownToggle, DropdownMenu, DropdownItem, NavbarText,
} from 'reactstrap';
import userContext from '../context/UserContext';

const CNavbar = () => {

  let navigate = useNavigate()
const userContextData = useContext(userContext)
  const [isOpen, setIsOpen] = useState(false);

  const toggle = () => setIsOpen(!isOpen);

  const [login, setLogin]=useState(false)
  const [user, setUser]=useState(null)

  useEffect(()=>{
    setLogin(isLoggedIn())
    setUser(getCurrentUserDetail())
    
  },[login])

  const logout = ()=>{
    doLogout(()=>{
      //loggedout
      setLogin(false)
      userContextData.setUser({
        data:null,
        login:false
      })
      navigate('/')
    })
  }

  return (
    <div>
      <Navbar color='dark' dark expand="md" fixed='' className='px-5'>
        <NavbarBrand tag={ReactLink} to="/">MyBlogs</NavbarBrand>
        <NavbarToggler onClick={toggle} />
        <Collapse isOpen={isOpen} navbar>
          <Nav className="me-auto" navbar>
            <NavItem>
              <NavLink tag={ReactLink} to="/">New Feed</NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={ReactLink} to="/about">About</NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={ReactLink} to="/services">Services</NavLink>
            </NavItem>
            {/* login and signup */}
            <UncontrolledDropdown nav inNavbar>
              <DropdownToggle nav caret>
                Contact us
              </DropdownToggle>
              <DropdownMenu end>
              <DropdownItem tag={ReactLink} to='https://www.linkedin.com/in/aashif-iqubal-b067bb22b/' target="_blank" rel="noopener noreferrer">
                LinkedIn
              </DropdownItem>

               {/* For Instagram */}
              <DropdownItem tag={ReactLink} to='https://www.instagram.com/aashif_857' target="_blank" rel="noopener noreferrer">
                Instagram
              </DropdownItem>

              {/* For twitter */}
              <DropdownItem tag={ReactLink} to='https://twitter.com/aashif_857' target="_blank" rel="noopener noreferrer">
                Twitter
              </DropdownItem>

                <DropdownItem divider />
                {/* <DropdownItem>Reset</DropdownItem> */}
              </DropdownMenu>
            </UncontrolledDropdown>
          </Nav>

          <Nav navbar>

            {
              login && (
            <>

            <NavItem>
              <NavLink tag={ReactLink} to={`/user/profile-info/${user.userId}`}>profileInfo</NavLink>
            </NavItem>
            
            <NavItem>
              <NavLink className='pr' color='green' tag={ReactLink} to="/user/dashboard" active={true}>{user.name}</NavLink>
            </NavItem>

            <NavItem style={{ cursor: 'pointer' }}>
              <NavLink onClick={logout}>Logout</NavLink>
            </NavItem>
            </>
              )
            }


            {
              !login && (
                <>
                  <NavItem>
                    <NavLink tag={ReactLink} to="/login">Login</NavLink>
                  </NavItem>
                  <NavItem>
                   <NavLink tag={ReactLink} to="/signup">Signup</NavLink>
                </NavItem>
                </>
              )
            }

          </Nav>

          {/* <NavbarText>Youtube</NavbarText> */}
        </Collapse>
      </Navbar>
    </div>
  );

}

export default CNavbar