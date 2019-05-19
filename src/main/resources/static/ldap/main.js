var util = {

    ajax: {
        // mutax for ajax
        mutex: {
        },

        // raw ajax
        raw : function(path, options, readType, res) {
            
            // 강제옵션
            options.credentials = "same-origin";
            
            // 기본옵션
            if (!options.method) {
                options.method = 'POST';
            }
            if (!options.headers) {
                options.headers = {};
            }
            if (!options.headers['Content-Type'] && !options.fileUploadMode) {
                options.headers['Content-Type'] = 'application/json';
            }
            
            if (typeof options.lock == 'string') {
                if (util.ajax.mutex[options.lock]) {
                    alert('it work is on progress, please wait a minute');
                    return;
                } else {
                    util.ajax.mutex[options.lock] = true;
                }
            }
            
            fetch(path, options)
                .then(function(e){
                    util.ajax.mutex[options.lock] = false;
                    if (e.status == 200) {
                        return e[readType]();
                    }
                    switch (status) {
                        case 403 : break;
                    }
                    throw 'ERROR';
                })
                .then(res)
                .catch(function(e){
                    console.log(e);
                    alert('An error has occurred.\nplease try again'); 
                });
        },

        text : function(path, options, res) {
            util.ajax.raw(path, options, 'text', res);
        },
        
        json : function(path, options, res) {
            util.ajax.raw(path, options, 'json', res);
        }
    }
    
    
    
};