package com.ecom.customerservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/customers")
public class CustomerController
{
    private static final Logger logger  = LoggerFactory.getLogger(CustomerController.class.getName());
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    Producer producer = new Producer();

    @PostMapping("/update") // URIs SERVE CHUNKS OF DATA UNLIKE URLs WHICH SERVE PAGES
    public ResponseEntity<String> updateCustomerDetails(@RequestBody Customer customer) throws JsonProcessingException {
        logger.info("initiating customer update in Product Catalog Controller");
        customerRepository.save(customer);
        logger.info(" product update completed successfully in customer Table");
        logger.info(customer.getCustomerName()," initiating product topic");
        producer.pubUpdateCustomerDetailsMessage(customer.getCustomerName(), "CUSTOMER DETAILS UPDATED SUCCESSFULLY");

        return ResponseEntity.ok("Details Updated Successfully");
    }

    //@GetMapping("get/restros")
    //public ResponseEntity<?> getRestros() throws InterruptedException
    //{
    //    Thread.sleep(1000);
     //   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    //}

    @GetMapping("/getAll")
    public ResponseEntity<Customer> getProduct(@PathVariable String id) {
        Customer customer = customerRepository.getReferenceById(id);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

                //new ResponseEntity(product.get(0));
                //product.map(ResponseEntity::ok)
               // .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
