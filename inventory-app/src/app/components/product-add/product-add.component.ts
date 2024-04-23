import { Component, OnInit} from '@angular/core';
import { Product } from 'src/app/common/product';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';
import { User } from 'src/app/common/user';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-product-add',
  templateUrl: './product-add.component.html',
  styleUrls: ['./product-add.component.css']
})
export class ProductAddComponent implements OnInit{

  product: Product = new Product(
    0, "", 0, new Date(), undefined
  )
  user!: User;
  isCreate: boolean = true;
  tittle: string = "Añadir Producto";

  constructor(private productService : ProductService, 
    private router:Router, 
    private activatedRoute:ActivatedRoute,
    private toastr: ToastrService){}

  ngOnInit(): void {
    this.getProductById();
  }

  getProductById() {
    this.activatedRoute.params.subscribe(
      prod => {
        let id = prod['id'];
        if(id){
          this.tittle = "Editar Producto"
          this.isCreate = false;
          this.productService.getProductById(id).subscribe(
            data => this.product = data
          );
        }
      }
    );
  }

  addProduct() {
    const userJSON = localStorage.getItem('user');
    if (userJSON !== null) {
      this.user = JSON.parse(userJSON);
    }
    const productData = {
      id: this.product.id,
      name: this.product.name,
      quantity: this.product.quantity,
      entryDate: this.product.entryDate,
      registeredBy: this.user,
      lastModifiedBy: this.isCreate ? null : this.user
    };

    this.productService.createProducts(productData).subscribe(
      data => {
        console.log(data);
        if(this.product.id==0){
          alert('Producto registrado correctamante');
        }else{
          alert('Producto actualizado correctamante');
        }
        this.router.navigate(['']);      
      }
    );
    
  }

}
