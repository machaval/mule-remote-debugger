#Documentation

## Configuration Properties

|Type|Name|Value|Desc|
|:---------:|:----------:|:----------:|:----------:|
|xs:string|name| |Optional. Give a name to this configuration so it can be later referenced|
|xs:int|portNumber|6666|Optional. The debugger port, by default is 6666|
|xs:boolean|suspend|false|Optional. If true the debugger will wait till some clients connects|

## Example

```
 <mule xmlns="http://www.mulesoft.org/schema/mule/core"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:spring="http://www.springframework.org/schema/beans"
      xmlns:debugger="http://www.mulesoft.org/schema/mule/debugger"
      xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
        http://www.mulesoft.org/schema/mule/core http://www.mulesoft.org/schema/mule/core/current/mule.xsd
        http://www.mulesoft.org/schema/mule/debugger http://www.mulesoft.org/schema/mule/debugger/current/mule-debugger.xsd">

    <debugger:config name="config"/>

    <flow name="testFlow">
        <debugger:breakpoint/>
    </flow>

 </mule>
```