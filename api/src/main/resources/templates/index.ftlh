[#ftl]

[#import "inc/page.ftlh" as p /]
[#import "inc/chat.ftlh" as c /]

[@p.page "Let's Chat!"; t]
    [#if t='body']
        <div class="pure-g">
            <div class="pure-u-1-3"></div>
            <div class="pure-u-1-3">
                <!-- Chat Container -->
                <div class="chat-container">

                    <!-- Header -->
                    <header class="chat-header">
                        <h1 class="chat-title">AI Demo</h1>
                    </header>

                    <!-- Chat History Area (Scrollable Content) -->
                    <main class="chat-history">

                    </main>

                    <div class="input-area">
                        <textarea
                                name="text"
                                placeholder="Type your message..."
                                class="message-input"
                                rows="1" ></textarea>

                        <button class="send-button" aria-label="Send Message"
                                hx-post="/chat"
                                hx-trigger="click"
                                hx-target=".chat-history"
                                hx-swap="beforeend scroll:bottom"
                                hx-include=".message-input"
                                hx-on:click="send()"
                                hx-on::before-send="clear()">
                            Send
                        </button>
                    </div>
                </div>
            </div>
            <div class="pure-u-1-3"></div>
        </div>
    [/#if]
    <script>
        function clear(){
            document.querySelector('.message-input').value = ""
        }
        function send() {
            const template = document.querySelector('#outTemplate > :first-child');
            const child = template.cloneNode(true);

            child.querySelector('.message-text').textContent = document.querySelector('.message-input').value
            child.querySelector('.message-time').textContent = new Date().toLocaleTimeString();

            const history = document.querySelector('.chat-history')

            history.appendChild(child)
            history.scrollTop = history.scrollHeight;
        }
    </script>
    <div id="outTemplate" hidden>
        [@c.messageOut time="" /]
    </div>
    <div id="inTemplate" hidden>
        [@c.messageOut time="" /]
    </div>
[/@p.page]