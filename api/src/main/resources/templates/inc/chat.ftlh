[#ftl]

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
            <p class="message-text">[#nested /]</p>
            <span class="message-time">${time}</span>
        </div>
    </div>
[/#macro]
