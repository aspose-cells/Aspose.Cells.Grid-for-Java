package com.example.gridjsdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

 
import com.aspose.gridjs.GridJsJakartaControllerBase;
import com.aspose.gridjs.GridJsService;

import jakarta.servlet.http.HttpServletRequest;
 
/**
 * REST controller that handles GridJs operations such as loading a workbook,
 * updating cells, managing images, and downloading files.
 */
@RestController
@RequestMapping("/GridJs")
public class GridJsController extends GridJsJakartaControllerBase {
    @Autowired
    public GridJsController(GridJsService gridJsService) {
        super(gridJsService);
    }
    /**
     * Loads a workbook and returns its JSON representation together with a unique id.
     *
     * @param filename Name of the Excel file located under the configured root folder.
     * @param uid      Unique identifier generated on the client side.
     * @throws Exception 
     */
    @GetMapping("/LoadSpreadsheet")
    public ResponseEntity<String> loadSpreadsheet(@RequestParam String filename,
                                                @RequestParam String uid) throws Exception {
//    	 String uid="2026040722.xlsx";
//    	String[] oprlist= new String[2];
//    	//todo add opr in oprlist
//    	com.aspose.gridjs.GridJsWorkbook  gwb=new com.aspose.gridjs.GridJsWorkbook();
//    	gwb.redactFile("c:/input.xlsx", uid, oprlist);
//    	String filejson=gwb.exportToJson(filename);
    	
    	
        String fullFilePath = getFullFilePath(filename);
        StringBuilder json = _gridJsService.detailFileJsonWithUid(fullFilePath, uid);
        return ResponseEntity.ok()
                .header("Content-Type", "text/plain; charset=UTF-8")
                .body(json.toString());
        
        
        
    }
    /**
     * Resolve the physical file path. Adjust the base folder to match your environment.
     */
    private String getFullFilePath(String filename) {
        // Example: All Excel files are placed under src/main/resources/files/
        return "./src/main/resources/files/" + filename;
    }
    // -------------------------------------------------------------------------
    // The following endpoints simply delegate to the base implementation.
    // -------------------------------------------------------------------------
    @PostMapping("/UpdateCell")
    public ResponseEntity<String> updateCell(HttpServletRequest request) {
        try {
            return super.updateCell(request);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/AddImage")
    public ResponseEntity<String> addImage(@RequestParam(value = "image", required = false) MultipartFile file,
                                           @RequestParam("uid") String uid,
                                           @RequestParam("p") String p,
                                           @RequestParam(value = "control", required = false) String isControl) {
        return super.addImage(file, uid, p, isControl);
    }
    @PostMapping("/CopyImage")
    public ResponseEntity<String> copyImage(HttpServletRequest request) {
        return super.copyImage(request);
    }
    @PostMapping("/AddImageByURL")
    public ResponseEntity<String> addImageByUrl(HttpServletRequest request) {
        return super.addImageByUrl(request);
    }
    @GetMapping("/Image")
    public ResponseEntity<InputStreamResource> getImage(HttpServletRequest request) {
        return super.getImage(request);
    }
    @GetMapping("/ImageUrl")
    public ResponseEntity<String> getImageUrl(@RequestParam String id,
                                               @RequestParam String uid) {
        return super.getImageUrl(id, uid);
    }
    @GetMapping("/Ole")
    public ResponseEntity<?> getOle(HttpServletRequest request) {
        return super.getOle(request);
    }
    @GetMapping("/GetFile")
    public ResponseEntity<?> getFile(@RequestParam("id") String id) {
        return super.getFile(id);
    }
    @PostMapping("/Download")
    public ResponseEntity<String> download(HttpServletRequest request) {
        return super.download(request);
    }
}
