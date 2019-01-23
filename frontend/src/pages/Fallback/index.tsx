import React from 'react'
import { Link } from 'react-router-dom'
import Typography from '@material-ui/core/Typography'

const Fallback = () => (
  <div>
    <Typography variant="h6">
      <p>
        404!
      </p>
    </Typography>
    <Link to="/">Back to the Index</Link>
  </div>
)

export default Fallback
