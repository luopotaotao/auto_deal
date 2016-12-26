/**
 * Created by tt on 2016/11/28.
 */
$.extend({
    getResumble:function (opts) {
        if(!(opts.url)){
            throw {
                msg:'url不可为空'
            };
        }
        if($.isArray(opts.fileType)){
            opts.fileType = $.grep(opts.fileType,function (item) {
                return item&&item[0]=='.';
            });
        }
        
        var r = new Resumable({
            target:opts.url,
            chunkSize:1024*1024*1024,
            simultaneousUploads:1,
            testChunks: true,
            throttleProgressCallbacks:1,
            method: "multipart",
            uploadMethod:'POST',
            maxChunkRetries:3,
            testChunks:false,
            fileType:opts.fileType,
            fileTypeErrorCallback:opts.fileTypeErrorHandler
        });
// Resumable.js isn't supported, fall back on a different method
        if(!r.support) {
            $('.resumable-error').show();
        } else {
            // Show a place for dropping/selecting files
            $('.resumable-drop').show();
            $('.resumable-browse').each(function (i,item) {
                r.assignBrowse(item);
            });
            $('.resumable-drop').each(function (i,item) {
                r.assignDrop(item);
            });
            $('.resumable-upload').bind('click',r.upload);
            $('.resumable-resume').bind('click',r.upload);
            $('.resumable-pause').bind('click',r.pause);
            $('.resumable-cancel').bind('click',r.cancel);
            
            // Handle file add event
            r.on('fileAdded', function(file){
                // Show progress pabr
                $('.resumable-progress, .resumable-list').show();
                // Show pause, hide resume
                $('.resumable-progress .progress-resume-link').hide();
                $('.resumable-progress .progress-pause-link').show();
                // Add the file to the list
                $('.resumable-list').append('<li class="resumable-file-'+file.uniqueIdentifier+'">Uploading <span class="resumable-file-name"></span> <span class="resumable-file-progress"></span>');
                $('.resumable-file-'+file.uniqueIdentifier+' .resumable-file-name').html(file.fileName);
                // Actually start the upload
                r.upload();
            });
            r.on('pause', function(){
                // Show resume, hide pause
                $('.resumable-progress .progress-resume-link').show();
                $('.resumable-progress .progress-pause-link').hide();
            });
            r.on('complete', function(){
                // Hide pause/resume when the upload has completed
                $('.resumable-progress .progress-resume-link, .resumable-progress .progress-pause-link').hide();
            });
            r.on('fileSuccess', function(file,message){
                if($.isFunction(opts.successHandler)){
                    opts.successHandler($.parseJSON(message).uuid);
                }
                $('.resumable-file-'+file.uniqueIdentifier+' .resumable-file-progress').html('(completed)');
            });
            r.on('fileError', function(file, message){
                // Reflect that the file upload has resulted in error
                $('.resumable-file-'+file.uniqueIdentifier+' .resumable-file-progress').html('(file could not be uploaded: '+message+')');
            });
            r.on('fileProgress', function(file){
                // Handle progress for both the file and the overall upload
                $('.resumable-file-'+file.uniqueIdentifier+' .resumable-file-progress').html(Math.floor(file.progress()*100) + '%');
                $('.progress-bar').css({width:Math.floor(r.progress()*100) + '%'});
            });

        }
        return r;
    }
})
