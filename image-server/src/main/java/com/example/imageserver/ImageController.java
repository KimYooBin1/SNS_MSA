package com.example.imageserver;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

	private final ImageStorageService imageService;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
		try {
			return ResponseEntity.ok(imageService.store(file));
		} catch (IOException e) {
			return ResponseEntity.status(500).body("Failed to upload image: " + e.getMessage());
		}
	}

	@GetMapping("/view/{imageId}")
	public ResponseEntity<Resource> getImage(@PathVariable String imageId,
		@RequestParam(value = "thumbnail", defaultValue = "false") Boolean isThumbnail) {
		Resource image = imageService.get(imageId, isThumbnail);
		return (image != null) ? ResponseEntity.ok().contentType(MediaType.parseMediaType("image/jpeg")).body(image) :
			ResponseEntity.notFound().build();
	}
}
