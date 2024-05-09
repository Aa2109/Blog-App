import React, { useEffect, useState } from 'react'
import { ListGroup, ListGroupItem } from 'reactstrap'
import { loadAllCategory } from '../ServerServices/Category-Services';
import { toast } from 'react-toastify';
import { Link } from 'react-router-dom';
const CategorySideMenu = () => {

  const [categories, setCategories]= useState([]);

  useEffect(()=>{
      loadAllCategory().then(data=>{
        setCategories([...data])
      }).catch(error=>{
        console.log(error);
        toast.error("Error in loading categories")
      })

  }, [])


  return (
    // <Base>
    <div>
      
      <ListGroup >
        <ListGroupItem tag={Link} to="/" action={true} className='border-0'>
          All Blogs
        </ListGroupItem>

        {categories && categories.map((cat, index)=>{
          return (
            <ListGroupItem tag={Link} to={'/category/'+cat.categoryId} 
            key={index} action={true} className='border-0'
            >
              {cat.categoryName}
            </ListGroupItem>
          )
        })}
      </ListGroup>
    </div>
    // </Base>
  )
}

export default CategorySideMenu