import styled from 'styled-components'

const styling = `
    display: inline-block;
    background-color: #CECECE;
    padding: 0.5rem;
    margin: 0.5rem;
    border-radius: 2px;
    overflow: hidden;
`

const StyledPropertyTag = styled.div`${styling}`
export const InlinePropertyTag = styled.span`${styling}`

export default StyledPropertyTag
