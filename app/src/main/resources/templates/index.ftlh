[#ftl]

[#import "inc/page.ftlh" as p /]

[@p.page "Let's Chat!"; t]
    [#if t='body']
        <div class="pure-g">
            <div class="pure-u-1-3"></div>
            <div class="pure-u-1-3">
                <!-- Chat Container -->
                <div class="chat-container">

                    <!-- Header -->
                    <header class="chat-header">
                        <h1 class="chat-title">Group Chat Demo</h1>
                    </header>

                    <!-- Chat History Area (Scrollable Content) -->
                    <main class="chat-history">

                        [@messageIn "10:01 AM"]
                            Hey everyone! Does anyone have the notes from yesterday's presentation? I missed the last part.
                        [/@messageIn]

                        [@messageOut "10:03 AM"]
                            I've got them right here! Just sent you a link. Check your inbox.
                        [/@messageOut]

                        [@messageIn "10:04 AM"]
                            Awesome, thanks! That saves me a ton of time. Did we decide on the deadline for the project?
                        [/@messageIn]

                        [@messageOut "10:05 AM"]
                            We finalized it for Friday at 5 PM. Don't forget to review the final checklist before submitting!
                        [/@messageOut]

                        [@messageIn "10:06 AM"]
                            Placeholder message to ensure the scrollbar appears correctly on different screens.
                        [/@messageIn]

                        [@messageOut "10:07 AM"]
                            And another one! Testing the responsiveness and bubble shape.
                        [/@messageOut]

                        [@messageIn "10:08 AM"]
                            Final check message.
                        [/@messageIn]

                    </main>

                    <div class="input-area">
                        <textarea
                            placeholder="Type your message..."
                            class="message-input"
                            rows="1" ></textarea>

                        <button class="send-button" aria-label="Send Message">
                            Send
                        </button>
                    </div>
                </div>
            </div>
            <div class="pure-u-1-3"></div>
        </div>
    [/#if]
[/@p.page]

[#macro messageIn time=""]
    [@message "in" time]
        [#nested /]
    [/@message]
[/#macro]
[#macro messageOut time=""]
    [@message "out" time ]
        [#nested /]
    [/@message]
[/#macro]
[#macro message direction="in" time=""]
    <div class="message-row ${direction}">
        <div class="message-bubble">
            <p>[#nested /]</p>
            <span class="message-time">${time}</span>
        </div>
    </div>
[/#macro]