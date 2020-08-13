import React from "react";
import axios from "axios";

export default class Joke extends React.Component {
    state = {
        joke: "Hello"
    };
    componentDidMount() {
        axios.get(`http://localhost:8080/weather/{city}`)
            .then(res => {
                console.log("res" + res)
                const joke = res.data;
                this.setState({ joke:JSON.stringify(joke) });
            })
    }

    render() {
        return (
            <p>{this.state.joke}</p>
        )
    }
}