/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


dojo.provide("custom.jsonRpcDemo");if(!dojo._hasResource["dojo.AdapterRegistry"]){dojo._hasResource["dojo.AdapterRegistry"]=true;dojo.provide("dojo.AdapterRegistry");dojo.AdapterRegistry=function(_1){this.pairs=[];this.returnWrappers=_1||false;};dojo.extend(dojo.AdapterRegistry,{register:function(_2,_3,_4,_5,_6){this.pairs[((_6)?"unshift":"push")]([_2,_3,_4,_5]);},match:function(){for(var i=0;i<this.pairs.length;i++){var _8=this.pairs[i];if(_8[1].apply(this,arguments)){if((_8[3])||(this.returnWrappers)){return _8[2];}else{return _8[2].apply(this,arguments);}}}throw new Error("No match found");},unregister:function(_9){for(var i=0;i<this.pairs.length;i++){var _b=this.pairs[i];if(_b[0]==_9){this.pairs.splice(i,1);return true;}}return false;}});}if(!dojo._hasResource["dojox.rpc.Service"]){dojo._hasResource["dojox.rpc.Service"]=true;dojo.provide("dojox.rpc.Service");dojo.declare("dojox.rpc.Service",null,{constructor:function(_c,_d){var _e;var _f=this;function processSmd(smd){smd._baseUrl=new dojo._Url(location.href,_e||".")+"";_f._smd=smd;for(var _11 in _f._smd.services){var _12=_11.split(".");var _13=_f;for(var i=0;i<_12.length-1;i++){_13=_13[_12[i]]||(_13[_12[i]]={});}_13[_12[_12.length-1]]=_f._generateService(_11,_f._smd.services[_11]);}};if(_c){if((dojo.isString(_c))||(_c instanceof dojo._Url)){if(_c instanceof dojo._Url){_e=_c+"";}else{_e=_c;}var _15=dojo._getText(_e);if(!_15){throw new Error("Unable to load SMD from "+_c);}else{processSmd(dojo.fromJson(_15));}}else{processSmd(_c);}}this._options=(_d?_d:{});this._requestId=0;},_generateService:function(_16,_17){if(this[_17]){throw new Error("WARNING: "+_16+" already exists for service. Unable to generate function");}_17.name=_16;var _18=dojo.hitch(this,"_executeMethod",_17);var _19=dojox.rpc.transportRegistry.match(_17.transport||this._smd.transport);if(_19.getExecutor){_18=_19.getExecutor(_18,_17,this);}var _1a=_17.returns||(_17._schema={});var _1b="/"+_16+"/";_1a._service=_18;_18.servicePath=_1b;_18._schema=_1a;_18.id=dojox.rpc.Service._nextId++;return _18;},_getRequest:function(_1c,_1d){var smd=this._smd;var _1f=dojox.rpc.envelopeRegistry.match(_1c.envelope||smd.envelope||"NONE");if(_1f.namedParams){if((_1d.length==1)&&dojo.isObject(_1d[0])){_1d=_1d[0];}else{var _20={};for(var i=0;i<_1c.parameters.length;i++){if(typeof _1d[i]!="undefined"||!_1c.parameters[i].optional){_20[_1c.parameters[i].name]=_1d[i];}}_1d=_20;}var _22=(_1c.parameters||[]).concat(smd.parameters||[]);if(_1c.strictParameters||smd.strictParameters){for(i in _1d){var _23=false;for(j=0;j<_22.length;j++){if(_22[i].name==i){_23=true;}}if(!_23){delete _1d[i];}}}for(i=0;i<_22.length;i++){var _24=_22[i];if(!_24.optional&&_24.name&&!_1d[_24.name]){if(_24["default"]){_1d[_24.name]=_24["default"];}else{if(!(_24.name in _1d)){throw new Error("Required parameter "+_24.name+" was omitted");}}}}}else{if(_1c.parameters&&_1c.parameters[0]&&_1c.parameters[0].name&&(_1d.length==1)&&dojo.isObject(_1d[0])){if(_1f.namedParams===false){_1d=dojox.rpc.toOrdered(_1c,_1d);}else{_1d=_1d[0];}}}if(dojo.isObject(this._options)){_1d=dojo.mixin(_1d,this._options);}var _25=_1c._schema||_1c.returns;var _26=_1f.serialize.apply(this,[smd,_1c,_1d]);_26._envDef=_1f;var _27=(_1c.contentType||smd.contentType||_26.contentType);return dojo.mixin(_26,{sync:dojox.rpc._sync,contentType:_27,headers:{},target:_26.target||dojox.rpc.getTarget(smd,_1c),transport:_1c.transport||smd.transport||_26.transport,envelope:_1c.envelope||smd.envelope||_26.envelope,timeout:_1c.timeout||smd.timeout,callbackParamName:_1c.callbackParamName||smd.callbackParamName,schema:_25,handleAs:_26.handleAs||"auto",preventCache:_1c.preventCache||smd.preventCache,frameDoc:this._options.frameDoc||undefined});},_executeMethod:function(_28){var _29=[];var i;for(i=1;i<arguments.length;i++){_29.push(arguments[i]);}var _2b=this._getRequest(_28,_29);var _2c=dojox.rpc.transportRegistry.match(_2b.transport).fire(_2b);_2c.addBoth(function(_2d){return _2b._envDef.deserialize.call(this,_2d);});return _2c;}});dojox.rpc.getTarget=function(smd,_2f){var _30=smd._baseUrl;if(smd.target){_30=new dojo._Url(_30,smd.target)+"";}if(_2f.target){_30=new dojo._Url(_30,_2f.target)+"";}return _30;};dojox.rpc.toOrdered=function(_31,_32){if(dojo.isArray(_32)){return _32;}var _33=[];for(var i=0;i<_31.parameters.length;i++){_33.push(_32[_31.parameters[i].name]);}return _33;};dojox.rpc.transportRegistry=new dojo.AdapterRegistry(true);dojox.rpc.envelopeRegistry=new dojo.AdapterRegistry(true);dojox.rpc.envelopeRegistry.register("URL",function(str){return str=="URL";},{serialize:function(smd,_37,_38){var d=dojo.objectToQuery(_38);return {data:d,transport:"POST"};},deserialize:function(_3a){return _3a;},namedParams:true});dojox.rpc.envelopeRegistry.register("JSON",function(str){return str=="JSON";},{serialize:function(smd,_3d,_3e){var d=dojo.toJson(_3e);return {data:d,handleAs:"json",contentType:"application/json"};},deserialize:function(_40){return _40;}});dojox.rpc.envelopeRegistry.register("PATH",function(str){return str=="PATH";},{serialize:function(smd,_43,_44){var i;var _46=dojox.rpc.getTarget(smd,_43);if(dojo.isArray(_44)){for(i=0;i<_44.length;i++){_46+="/"+_44[i];}}else{for(i in _44){_46+="/"+i+"/"+_44[i];}}return {data:"",target:_46};},deserialize:function(_47){return _47;}});dojox.rpc.transportRegistry.register("POST",function(str){return str=="POST";},{fire:function(r){r.url=r.target;r.postData=r.data;return dojo.rawXhrPost(r);}});dojox.rpc.transportRegistry.register("GET",function(str){return str=="GET";},{fire:function(r){r.url=r.target+(r.data?"?"+r.data:"");return dojo.xhrGet(r);}});dojox.rpc.transportRegistry.register("JSONP",function(str){return str=="JSONP";},{fire:function(r){r.url=r.target+((r.target.indexOf("?")==-1)?"?":"&")+r.data;r.callbackParamName=r.callbackParamName||"callback";return dojo.io.script.get(r);}});dojox.rpc.Service._nextId=1;dojo._contentHandlers.auto=function(xhr){var _4f=dojo._contentHandlers;var _50=xhr.getResponseHeader("Content-Type");results=!_50?_4f.text(xhr):_50.match(/\/.*json/)?_4f.json(xhr):_50.match(/\/javascript/)?_4f.javascript(xhr):_50.match(/\/xml/)?_4f.xml(xhr):_4f.text(xhr);return results;};}if(!dojo._hasResource["dojox.rpc.JsonRPC"]){dojo._hasResource["dojox.rpc.JsonRPC"]=true;dojo.provide("dojox.rpc.JsonRPC");(function(){function jsonRpcEnvelope(_51){return {serialize:function(smd,_53,_54,_55){var d={id:this._requestId++,method:_53.name,params:_54};if(_51){d.jsonrpc=_51;}return {data:dojo.toJson(d),handleAs:"json",contentType:"application/json",transport:"POST"};},deserialize:function(obj){if("Error"==obj.name){obj=dojo.fromJson(obj.responseText);}if(obj.error){var e=new Error(obj.error.message||obj.error);e._rpcErrorObject=obj.error;return e;}return obj.result;}};};dojox.rpc.envelopeRegistry.register("JSON-RPC-1.0",function(str){return str=="JSON-RPC-1.0";},dojo.mixin({namedParams:false},jsonRpcEnvelope()));dojox.rpc.envelopeRegistry.register("JSON-RPC-2.0",function(str){return str=="JSON-RPC-2.0";},jsonRpcEnvelope("2.0"));})();}if(!dojo._hasResource["custom.jsonrpcDemo"]){dojo._hasResource["custom.jsonrpcDemo"]=true;dojo.provide("custom.jsonrpcDemo");}