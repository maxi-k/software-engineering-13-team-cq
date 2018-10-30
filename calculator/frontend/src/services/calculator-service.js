const backendUrl = 'https://st-calculator-backend.herokuapp.com/eval'

const evaluate = (operator, op1, op2) => {
    return fetch(backendUrl + '?e=' + encodeURIComponent(op1 + operator + op2), {
        mode: 'no-cors',
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
    })
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
    return Promise.resolve(
        new Response(JSON.stringify({ value: result })),
        { status: 200 }
    )
}

export { evaluate, mockEvaluate }
