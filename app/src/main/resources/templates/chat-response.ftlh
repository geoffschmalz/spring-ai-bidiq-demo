[#ftl]

[#import "inc/chat.ftlh" as c /]

[@c.messageIn time="${now?time}" ]
    ${response?replace("\n", "<br>")?no_esc}
[/@c.messageIn]