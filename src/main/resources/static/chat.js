

var socket = new SockJS("/websocket")
var stompClient = Stomp.over(socket)
var questionBtn = document.querySelector("#question")
var submitBtn = document.querySelector("#send")
var name=new URLSearchParams(location.search).get("name") //처음 페이지에서 입력한 이름 가져옴
document.querySelector("#name").innerText=`당신의 이름 : ${name}`
document.querySelector("#count").innerText=`남은 횟수 : 20`
questionBtn.addEventListener("click", sendQuestion)
submitBtn.addEventListener("click", sendMessage)
stompClient.connect({}, function (frame) {
    console.log(frame);
    stompClient.subscribe("/simple/chat", function (message) {
        console.log(JSON.parse(message.body).sender)
        onMessage(JSON.parse(message.body))
    });
    stompClient.send("/app/enter",{},
        JSON.stringify({
            "sender" : name,
            "profile" : "asdf" //이미지를 바이너리 데이터로 변환해서 보냄
        })
    )
});

function sendMessage(e){
        var sendBtnValue = document.querySelector("#chat").value
        stompClient.send("/app/chat",{},
            JSON.stringify({
                "sender" : name,
                "content" : sendBtnValue,
                "profile" : "asdf" //이미지를 바이너리 데이터로 변환해서 보냄
            })
        )
}
function sendQuestion(e){
        var sendBtnValue = document.querySelector("#chat").value
        stompClient.send("/app/question-submit",{},
            JSON.stringify({
                "sender" : name,
                "content" : sendBtnValue,
                "profile" : "asdf" //이미지를 바이너리 데이터로 변환해서 보냄
            })
        )
}
function onMessage(message){
    const tr = document.createElement("tr")
    const role = document.createElement("td")
    role.innerText=message.role
    const profile = document.createElement("td")
    const img = document.createElement("img")
    img.src=message.profile
    img.style="width:40px; height:40px;"
    profile.appendChild(img)
    const sender = document.createElement("td")
    sender.innerText=message.sender
    const message2 = document.createElement("td")
    message2.innerText=message.content
    tr.appendChild(role)
    tr.appendChild(profile)
    tr.appendChild(sender)
    tr.appendChild(message2)
    const body = document.querySelector("#chat-body")
    body.appendChild(tr)
    document.querySelector("#count").innerText=`남은 횟수 : ${message.count}`
    console.log(message.count)
    if (message.sender==name){ //내가 보낸 메시지만 역할  세팅
        document.querySelector("#role").innerText=`당신의 역할 : ${message.role}`
    }

}
