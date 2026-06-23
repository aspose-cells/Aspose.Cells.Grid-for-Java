// src/main/resources/static/gridjs-demo.js
/**
 * GridJs Demo – client side logic.
 * Loads a workbook from the server, displays it, and enables server‑side updates.
 */
 let xs;   
$(function () {
    // -------------------------------------------------------------
    // 1. Define URLs used by the demo
    // -------------------------------------------------------------
    const queryJsonUrl = "/GridJs/LoadSpreadsheet";
    const updateUrl = "/GridJs/UpdateCell";
    const fileDownloadUrl = "/GridJs/Download";
    const oleDownloadUrl = "/GridJs/Ole";
    const imageUrl = "/GridJs/ImageUrl";
    const imageUploadUrl1 = "/GridJs/AddImage";
    const imageUploadUrl2 = "/GridJs/AddImageByURL";
    const imageCopyUrl = "/GridJs/CopyImage";
    // -------------------------------------------------------------
    // 2. Global GridJs instance placeholder
    // -------------------------------------------------------------
    // will hold the x_spreadsheet instance
    let uid='';
    // -------------------------------------------------------------
    // 3. Helper to generate a UUID (used as unique session id)
    // -------------------------------------------------------------
    function generateUUID() {
        // RFC4122 version 4 compliant UUID
        return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
            (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
        );
    }
    
	function jumpWithUid() {
		// 1. 获取当前 URL 的查询参数字符串 (即 ? 后面的部分)
		const params = new URLSearchParams(window.location.search);

		// 2. 获取 uid 的值
		uid = params.get('uid');
		let fname = params.get('filename')??'Sample.xlsx';
		 
		$("#filename").val(fname);

		if (uid) {
			console.log("get uid:", uid);
			setTimeout(function() {
				$("#loadBtn").trigger("click");
			}, 500);


		} else {
			uid = generateUUID();


			const currentUrl = window.location.href;



			const urlObj = new URL(currentUrl);
			urlObj.searchParams.set('uid', uid);


			window.location.href = urlObj.toString();
		}




	}

	jumpWithUid();
    // -------------------------------------------------------------
    // 4. Load Spreadsheet button click handler
    // -------------------------------------------------------------
    $("#loadBtn").on("click", function () {
		$("#loadbtndiv").hide();
        const filename = $("#filename").val().trim();
        if (!filename) {
            alert("Please enter a file name.");
            return;
        }
        console.log("get uid in btn click:", uid); 
        console.log("get file  in btn click:", filename); 
        // unique identifier for this session
        // Build request URL with query parameters
        const requestUrl = `${queryJsonUrl}?filename=${encodeURIComponent(filename)}&uid=${uid}`;
        // ---------------------------------------------------------
        // 5. AJAX request to obtain the JSON representation of the workbook
        // ---------------------------------------------------------
		$.ajax({
			url: requestUrl,
			method: "GET",
			dataType: "text", // server returns plain text JSON
			success: function(responseJsonString) {
				const jsondata = JSON.parse(responseJsonString);
				const option = {
					updateMode: 'server',
					updateUrl: updateUrl,
					local: 'en', // UI language
					mode: 'read',
					enableRedactionShape: true,
					redactionDefaultColor: 'green',
					redactionReasons: [
						'Personal Info',
						'Confidential',
						'Legal Privilege',
						'Trade Secret',
					],
				};
				loadWithOption(jsondata, option, uid);
			},
			error: function(xhr, status, err) {
				console.error("Failed to load spreadsheet:", err);
				alert("Error loading file: " + err);
			}
		});
	});
    // -------------------------------------------------------------
    // 6. Function that creates the GridJs UI and binds all URLs
    // -------------------------------------------------------------
    function loadWithOption(jsondata, option, uniqueId) {
        // Clear any previous instance
        $('#gridjs-demo-uid').empty();
        const sheets = jsondata.data;
        const filename = jsondata.filename;
        const activeSheetName = jsondata.actname;
        // Initialise the GridJs UI, load data and set active sheet
        xs = x_spreadsheet('#gridjs-demo-uid', option)
            .loadData(sheets,activeSheetName)
            .updateCellError((msg) => console.error(msg));
        // Hide sheet tabs if server says not to show them
        if (!jsondata.showtabs) {
            xs.bottombar.hide();
        }
        // Associate the unique identifier and file name
        xs.setUniqueId(uniqueId);
        xs.setFileName(filename);
        // ---------------------------------------------------------
        // Set the active  cell (fallback to  A1)
        // ---------------------------------------------------------
        if (xs.getActiveSheetName() == activeSheetName) {
            xs.setActiveCell(jsondata.actrow, jsondata.actcol);
        } else {
			//some times if the active sheet is invisible or chartworksheet ,just use the first worksheet
            // If the requested sheet is hidden or a chart sheet,it will set the first visilbe sheet , just set A1 as active sheet
           xs.setActiveCell(0, 0);
        }
        // ---------------------------------------------------------
        // Bind auxiliary URLs (image handling, OLE, file download, etc.)
        // ---------------------------------------------------------
        xs.setImageInfo(imageUrl, imageUploadUrl1, imageUploadUrl2, imageCopyUrl, true);
        xs.setFileDownloadInfo(fileDownloadUrl);
        xs.setOleDownloadInfo(oleDownloadUrl);
        xs.setOpenFileUrl("/"); // optional: opens home page
        xs.on('redaction-inserted', (name,s) => {

                console.log('redaction-inserted:', s,' at sheet:',name);



            }).on('redaction-updated', (name, s) => {

                console.log('redaction-updated:', s, ' at sheet:',name);



            }).on('redaction-deleted', (name, s) => {

                console.log('redaction-deleted:', s,' at sheet:', name);



            });
    }
});