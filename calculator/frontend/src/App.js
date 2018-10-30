import React, { Component } from 'react';
import './normalize.css'
import './App.scss';
import { Provider } from 'react-redux'
import { store } from './state'

import Calculator from './components/Calculator'
import Header from './components/Header'

class App extends Component {
    render() {
        return (
            <Provider store={store}>
                <div className="App">
                    <Header />
                    <Calculator />
                </div>
            </Provider>
        );
    }
}

export default App;
