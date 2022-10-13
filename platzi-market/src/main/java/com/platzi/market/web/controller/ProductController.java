package com.platzi.market.web.controller;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping()
    @ApiOperation("Get all supermarket products || Muestra todos los productos de supermercado")
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity< List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    @ApiOperation("Search a product with an ID || buscar un producto por su ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "PRODUCT NOT FOUND"),
    })
    public ResponseEntity<Product> getProduct(@PathVariable("productId") int productId){
        return productService.getProduct(productId).map(product -> new ResponseEntity<>(product, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{categoryId}")
    @ApiOperation("Search all products corresponding to a category || Buscar todos los productos correspondientes a una categoria")
    public ResponseEntity<List<Product>> getByCategory(@ApiParam(value = "The id of the product", required = true, example = "7") @PathVariable("categoryId") int categoryId){
        return productService.getByCategory(categoryId).map(products -> new ResponseEntity<>(products, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    @ApiOperation("Create a new supermarket product || Crear un nuevo producto del supermercado")
    public ResponseEntity<Product> save(@RequestBody Product product){
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{productId}")
    @ApiOperation("Delete a product by ID || Elimina un producto por su ID")
    public ResponseEntity delete(@PathVariable("productId") int productId){
        if (productService.delete(productId)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
