$.extend($.fn.validatebox.defaults.rules, {
    LOGINNAME: {
        validator: function (value, param) {
    	//add by zhangxiaohui 2014/10/31
            return /^[a-zA-Z_]{4,20}$/.test(value);
        },
        message: '请输入4-20位的英文字母'
    },
    PWD: {
        validator: function (value, param) {
	    	if(value.length>=6) {
	            return true; 
	    	} else {
	    		return false;
	    	}
        },
        message: '密码长度不能小于6位'
    },
    APPLYTYPE_NAME: {
        validator: function (value, param) {
	    	if(value.length>=4 && value.length<=20)  {
	            return true; 
	    	} else {
	    		return false;
	    	}
        },
        message: '类型名称长度4-20位'
    },
    SETPWD: {
        validator: function (value, param) {
	    	if(value.length>=8) 
	    	{
	    		if(value.length<=20)
	    		{
	    			if(/[0-9]/.test(value)&&/[a-z]/.test(value)&&/[A-Z_]/.test(value)&&/[|\\`~!@#$%^&*()_+?:"{},.\/;'[\]=-]/.test(value))
	    			{
	    				return true;
	    			}
	    			else 
					{
						return false;
					}
	    		}
	    		else
				{
					return false;
				}
	    	} 
	    	else 
    		{
	    		return false;
	    	}
        },
        message: '密码长度为8-20位且应混合大小写字母、数字、特殊字符'
    },
    QQ: {
        validator: function (value, param) {
            return /^[1-9]\d{4,10}$/.test(value);
        },
        message: 'QQ号码不正确'
    },
    MOBILE : {// 验证手机号码 
        validator : function(value,param) { 
            return /^(13|15|18|14|17)\d{9}$/i.test(value); 
        }, 
        message : '手机号码格式不正确' 
    },
    AID : {// 验证AID（32位十六进制 偶数位）
    	validator : function(value,param) { 
        	if(value.length%2==0) {
                return /^[0-9a-fA-F]{32}$/i.test(value); 
        	} else {
        		return false;
        	}
        }, 
        message : '请输入32位16进制数'  
    },
    TAR : {// 验证TAR（6位十六进制）
        validator : function(value,param) { 
        		return /^[0-9a-fA-F]{6}$/i.test(value);
        }, 
        message : '请输入6位16进制数' 
    },    
    PAID : {// 10-32位十六进制(偶数位)
        validator : function(value,param) { 
        	if(value.length%2==0) {
                return /^[0-9a-fA-F]{10,32}$/i.test(value); 
        	} else {
        		return false;
        	}
        }, 
        message : '请输入10-32位16进制数' 
    },SPI : {// 4位十六进制(偶数位)
        validator : function(value,param) { 
        	if(value.length%2==0) {
                return /^[0-9a-fA-F]{4}$/i.test(value); 
        	} else {
        		return false;
        	}
        }, 
        message : '请输入4位16进制数' 
    },KIC : {// 2位十六进制(偶数位)
        validator : function(value,param) { 
        	if(value.length%2==0) {
                return /^[0-9a-fA-F]{2}$/i.test(value); 
        	} else {
        		return false;
        	}
        }, 
        message : '请输入2位16进制数' 
    },
    Hexadecimal : {// 十六进制(偶数位)
        validator : function(value,param) { 
        	if(value.length%2==0) {
                return /^[0-9a-fA-F]*$/i.test(value); 
        	} else {
        		return false;
        	}
        }, 
        message : '请输入16进制数' 
    },
    NUMNOZERO:{
    	  validator : function(value,param) { 
            return /^[1-9][0-9]*$/.test(value); 
        }, 
        message : '请输入数字' 
    },
    NUM:{
  	  validator : function(value,param) { 
          return /^[0-9]*$/.test(value); 
      }, 
      message : '请输入数字' 
    },
    NUM_one :{
        validator : function(value,param) { 
            return /^[0-9]{1}$/i.test(value); 
        }, 
        message : '请输入1位整数' 
    },
    NUM_two :{
        validator : function(value,param) { 
            return /^[0-9]{2}$/i.test(value); 
        }, 
        message : '请输入2位整数' 
    },
    HEX_NN :{
        validator : function(value,param) { 
            return /^([0-9a-fA-F][0-9a-fA-F])*$/i.test(value); 
        }, 
        message : '请输入十六进制数' 
    },
    IPPORT:{
    	validator : function(value,param) { 
        	return 	/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]):\d+$/.test(value);
    	},
    	message : '格式：IP:PORT'
    },
    IP:{
    	validator : function(value,param) { 
        	return 	/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test(value);
    	},
    	message : '例：192.168.199.2'
    },
    IPs:{
    	validator : function(value,param) { 
        	  return 	/^(((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]))\;)*((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]))$/i.test(value);
    		
    		},
    	message : '请输入正确的IP(例：192.168.199.2),多个IP用英文“;”隔开'
    },
    TELEPHONE:{
    	validator : function(value,param) { 
    		return 	 /^([\d]{3,4})-([\d]{7,8})$/.test(value);
    	},
    	message : '请输入正确号码,格式:区号-号码'
    },
    VERSION :{
        validator : function(value,param) { 
        	if(/^([0-9]{1,2}\.[0-9]{1,2})$/.test(value)){
        		return true;
        	}else{
        		if(/^[0-9]*$/.test(value)){
        			return true;
        		}else{
        			return false;
        		}
        	}
        }, 
        message : '请输入版本号(例：1.0或1.10或10 )' 
    },MODULE_AID :{
        validator : function(value,param) { 
        	if(value.replace(/\;/g,'').length%2==0) {
                return /^(([0-9a-fA-F]{10,32})\;)*([0-9a-fA-F]{10,32})$/i.test(value); 
         	}else {
         		return false;
         	}
        }, 
        message : '请输入正确的格式(AID为10-32位16进制数，多个AID请用英文“;”隔开)' 
    },EQUALTO: {
        validator: function (value, param) {
            return value == $(param[0]).val();
        },
        message: '两次输入的值不一致'
    },
    CMKSEQ : {// 验证cmk sequence（2位十六进制）
        validator : function(value,param) { 
    		return /^[1-9][0-9]*$/.test(value); 
        }, 
    	message : '请输入数字' 
    },
    NUM_four :{
        validator : function(value,param) { 
            return /^[0-9]{4}$/i.test(value); 
        }, 
        message : '请输入4位整数' 
    },
    NUM_three :{
        validator : function(value,param) { 
            return /^[0-9]{3}$/i.test(value); 
        }, 
        message : '请输入3位整数' 
    },
    KEY_COMPONENT_ONE : {// 16位十六进制(偶数位)
        validator : function(value,param) { 
        	if(value.length%2==0) {
                return /^[0-9a-fA-F]{16}$/i.test(value); 
        	} else {
        		return false;
        	}
        }, 
        message : '请输入16位16进制数' 
    },
    KEY_COMPONENT_TWO : {// 32位十六进制(偶数位)
        validator : function(value,param) { 
        	if(value.length%2==0) {
                return /^[0-9a-fA-F]{32}$/i.test(value); 
        	} else {
        		return false;
        	}
        }, 
        message : '请输入32位16进制数' 
    },
    NUM_ten :{
        validator : function(value,param) { 
            return /^[0-9]{10}$/i.test(value); 
        }, 
        message : '请输入10位整数' 
    },
    NO_SPACE :{
        validator : function(value,param) { 
            return /^\S+$/.test(value); 
        }, 
        message : '输入的信息内不能有空格' 
    },
    NUM_STR: {
        validator: function (value, param) {
            return /^[0-9a-zA-Z]*$/.test(value);
        },
        message: '请输入数字或字母'
    },
    PORT:{
    	validator : function(value,param) { 
        	return 	/^[0-9]*$/.test(value);
    	},
    	message : '请输入正确的端口号'
    },
    SEID:{
    	validator : function(value,param) { 
        	return 	/^[0-9a-fA-F]{12}$/.test(value);
    	},
    	message : '请输入正确的SEID信息(长度12位)'
    },
    EMAIL:{
    	validator:function (value){
    	       var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    	       return reg.test(value);
    	},
        message: '邮箱格式不正确'
    },
    URLorIPPORT:{
    	validator:function (value,param){
    		var str = "input:radio[name='";
    		str += param[0];
    		str += "']:checked";
    		
    		if($(str).val()==1)
    		{
    			return 	/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]):\d+$/.test(value);
    		}
			else
			{
				return /^(https|http):\/\/(((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]):\d+)|((\w+(-\w+)*)(\.(\w+(-\w+)*))*(\?\S*)?))\/.*wsdl$/i.test(value);
			}
    	},
    	message : "您输入的TCP/IP或WebServiceUrl格式不正确"
    
    	
    },
    SD_PRIVILEGES:{
    	validator : function(value,param) { 
        	if(value.length%2==0) {
                return /^[0-9a-fA-F]{5}[0]*$/i.test(value); 
        	} else {
        		return false;
        	}
        }, 
        message: '权限串格式不正确'
    },
    HASH : {// 十六进制(偶数位)
        validator : function(value,param) { 
        	if(value.length%2==0) {
                return /^[0-9a-fA-F]{40}$/i.test(value); 
        	} else {
        		return false;
        	}
        }, 
        message : '请输入40位正确的HASH值' 
    },
    SPID: {
        validator: function (value, param) {
            return /^[0-9a-zA-Z]{0,8}$/.test(value);
        },
        message: '请输入0-8位数字或字母'
    },
    BID: {// 验证系统编码（10位十六进制 偶数位）
    	validator : function(value,param) { 
        	if(value.length%2==0) {
                return /^[0-9a-fA-F]{10}$/i.test(value); 
        	} else {
        		return false;
        	}
        }, 
        message : '请输入10位16进制数'  
    },
    ZIP: {
        validator: function (value, param) {
            return /^\d{6}$/.test(value);
        },
        message: '邮政编码格式不正确'
    },
    HANZI_ZIMU_SHUZI :{
        validator : function(value,param) { 
        	return /^[a-zA-Z0-9\u4e00-\u9fa5]*$/.test(value); 
        }, 
        message : '请输入汉字或字母或数字' 
    },
    UPPERSTR_UNDERLINE :{
        validator : function(value,param) { 
        	return /^[A-Z_]*$/.test(value); 
        }, 
        message : '请输入大写字母或下划线' 
    },
    URL: {
        validator: function (value, param) {
        	//var reg =  /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;
        	var reg = /^(https|http|ftp|rtsp|mms){1}:\/\/(([0-9a-z_!~*\'().&=+$%-]+: )?[0-9a-z_!~*\'().&=+$%-]+@)?(([0-9]{1,3}.){3}[0-9]{1,3}|([0-9a-z_!~*\'()-]+.)*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z].[a-z]{2,6})(:[0-9]{1,4})?((\/?)|(\/[0-9a-z_!~*\'().;?:@&=+$,%#-]+)+\/?)$/; 
        	return reg.test(value);
        },
        message: 'URL格式不正确'
    },
    SEIDorMISDN:{
    	validator:function (value,param){
    		var str = "input:radio[name='";
    		str += param[0];
    		str += "']:checked";
    		
    		if($(str).val()==1)
    		{
    			return 	/^[0-9a-fA-F]{12}$/.test(value);
    		}
			else
			{
				return /^(13|15|18|14|17)\d{9}$/i.test(value);
			}
    	},
    	message : "您输入的SEID或MISDN格式不正确"
    
    	
    }
});