import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/common/product';
import { User } from 'src/app/common/user';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit{

  products: Product [] = [];
  product!: Product;
  user!: User;
  selectedUser: boolean = false;
  searchName: string = "";
  searchEntryDate: any;
  searchRegisteredBy: any;
  searchNameDisabled: boolean = false;
  searchEntryDateDisabled: boolean = false;
  searchRegisteredByDisabled: boolean = false;

  constructor(private productService:ProductService){}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe(
      data => this.products = data
    );
  }

  onFieldChange(field: string, event: KeyboardEvent): void {
    const value = (event.target as HTMLInputElement).value;
    
    if (value.trim() === '') {
      this.activateAllFields();
      this.loadProducts();
    } else {
      this.activateField(field);
    }
    this.applyFilter();
  }

  activateField(field: string): void {
    this.searchNameDisabled = field !== 'name';
    this.searchEntryDateDisabled = field !== 'entryDate';
    this.searchRegisteredByDisabled = field !== 'registeredBy';
  }

  activateAllFields(): void {
    this.searchNameDisabled = false;
    this.searchEntryDateDisabled = false;
    this.searchRegisteredByDisabled = false;
  }

  applyFilter(): void {
    const filters: { [key: string]: (product: Product) => boolean } = {
      'name': (product) => product.name.toLowerCase().includes(this.searchName.toLowerCase()),
      'entryDate': (product) => {
        const searchTerm = this.searchEntryDate.toLowerCase().trim();
        if (!searchTerm) return true; // Si no se proporciona un término de búsqueda, no se aplica filtro por fecha de ingreso
        const formattedSearchDate = searchTerm.split('/').reverse().join('-'); // Convertir el término de búsqueda a formato yyyy-mm-dd
        const productDate = new Date(product.entryDate);
        const formattedProductDate = productDate.toISOString().split('T')[0]; // Obtener la fecha del producto en formato yyyy-mm-dd
        return formattedProductDate.includes(formattedSearchDate);
      },
      'registeredBy': (product) => (product.registeredBy?.name || '').toLowerCase().includes(this.searchRegisteredBy.toLowerCase())
    };
  
    let filteredProducts = this.products;
  
    for (const [field, filter] of Object.entries(filters)) {
      const searchTerm = ((this['search' + (field.charAt(0).toUpperCase() + field.slice(1)) as keyof ProductListComponent] as string) || '').trim();
      if (searchTerm) {
        filteredProducts = filteredProducts.filter(filter);
      }
    }
  
    this.products = filteredProducts;
  }  
  

  deleteProductById(productId: number): void {
    const userJSON = localStorage.getItem('user');
    if (userJSON !== null) {
      this.user = JSON.parse(userJSON);
    }
    console.log(productId);
    this.productService.deleteProduct(productId, this.user.id).subscribe({
      next: () => {
        this.loadProducts();
        alert('Producto eliminado exitosamente.');
      },
      error: error => {
        alert('Ocurrió un error al eliminar el producto. Usuario no permitido para la operacion');
      }
    });
    this.loadProducts();
  }

  editProduct(productEdit: Product) {
    this.product = productEdit;
  }

}
