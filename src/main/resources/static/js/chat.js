var chat = {

    ws : undefined,

    userId : undefined,

    selector : "message",

    init: function(){
        let that = this;
        that.wsOpen();
        that.bindEvents();
        that.userId = "tempUser";
    },

    bindEvents: function(){
        let that = this;
        $("#messageContainer #sendBtn",that.selector).on("click",function(){
            that.sendMessage();
        });

        document.addEventListener("keypress", function(e){
            if(e.keyCode == 13){ //enter press
                that.sendMessage();
            }
        });
    },

    wsOpen: function(){
        let that = this;
        that.ws = new WebSocket("ws://" + location.host + "/chating");
        console.log('호스트 : ',location.host);
        that.wsEvt();
    },

    wsEvt: function(){
        let that = this;
        that.ws.onopen = function(data){
            //소켓이 열리면 초기화 세팅하기
        }

        that.ws.onmessage = function(data) {
            let msg = data.data;
            if(msg != null && msg.trim() != ''){
                $("#chating").append("<p>" + msg + "</p>");
            }
        }
    },

    sendMessage: function(){
        let that = this;
        let msg = $("#messageContainer #chatting").val();
        that.ws.send(that.userId + " : " + msg);
        $('#messageContainer #chatting').val("");
    }
}