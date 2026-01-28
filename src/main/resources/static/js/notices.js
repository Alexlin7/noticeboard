function confirmDelete(event) {
    event.preventDefault(); // Prevent form submission
    const form = event.target;
    Swal.fire({
        title: '確定要刪除嗎？',
        text: "此操作無法復原！",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: '是的，刪除！',
        cancelButtonText: '取消'
    }).then((result) => {
        if (result.isConfirmed) {
            form.submit();
        }
    });
    return false;
}

/**
 * Handles image uploads from the Summernote editor.
 * @param {File} file The file to upload.
 * @param {Object} editor The Summernote editor instance.
 */
function uploadImage(file, editor) {
    const formData = new FormData();
    formData.append('file', file);

    fetch('/api/images/upload', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Server responded with status ' + response.status);
        }
        return response.json();
    })
    .then(data => {
        if (data.url) {
            // Insert the image into the editor using the final URL
            $(editor).summernote('insertImage', data.url);
        } else {
            throw new Error('URL not found in response');
        }
    })
    .catch(error => {
        console.error('Error uploading image:', error);
        Swal.fire('上傳失敗', '圖片上傳時發生錯誤，請稍後再試。', 'error');
    });
}

$(document).ready(function() {
    // Initialize Datepickers
    $('.datepicker').datepicker({
        format: 'yyyy-mm-dd',
        language: 'zh-TW',
        autoclose: true,
        todayHighlight: true
    });

    // Initialize Summernote
    $('#content').summernote({
        height: 300,
        tabsize: 2,
        callbacks: {
            onImageUpload: function(files) {
                // 'this' refers to the editor instance.
                uploadImage(files[0], this);
            }
        }
    });
});
