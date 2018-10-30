const backendUrl = ''

const evaluate = (operator, op1, op2) => {
    return fetch(backendUrl + '?e=' + op1 + operator + op2).json()
}

const mockEvaluate = (operator, op1, op2) => {
    let result = 0;
    switch(operator) {
    case '*':
        result = parseInt(op1) * parseInt(op2);
        break;
    case '+':
    default:
        result = parseInt(op1) + parseInt(op2);
        break;
    }
    return Promise.resolve({
        result
    })
}

export { evaluate, mockEvaluate }
