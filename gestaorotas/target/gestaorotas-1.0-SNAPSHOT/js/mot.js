/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener("DOMContentLoaded", function () {
    const chatBox = document.getElementById("chatBox");
    const chatInput = document.getElementById("chatInput");
    const sendButton = document.getElementById("sendButton");

    const ws = new WebSocket("ws://localhost:8080/gestaorotas/chat");

    ws.onmessage = function (event) {
        const messageData = JSON.parse(event.data);
        displayMessage(messageData.message, messageData.sender);
    };

    sendButton.addEventListener("click", function () {
        const message = chatInput.value;
        const messageData = {
            message: message,
            sender: "user" // ou "driver" dependendo de quem está enviando a mensagem
        };

        ws.send(JSON.stringify(messageData));
        displayMessage(message, "user");
        chatInput.value = "";
    });

    function displayMessage(message, sender) {
        const messageElement = document.createElement("div");
        messageElement.classList.add("message", sender);
        messageElement.innerHTML = `<p>${message}</p>`;
        chatBox.appendChild(messageElement);
        chatBox.scrollTop = chatBox.scrollHeight;
    }
});

