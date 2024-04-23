import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { HttpClientModule } from '@angular/common/http'
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { UserListComponent } from './components/user-list/user-list.component';
import { ProductAddComponent } from './components/product-add/product-add.component';
import { RouterModule, Routes } from '@angular/router';
import { ProductListComponent } from './components/product-list/product-list.component';
import { ToastrModule } from 'ngx-toastr';

const routes : Routes = [
  {path:'', component:HomeComponent},
  {path:'product', component: HomeComponent},
  {path:'product/addproduct', component: ProductAddComponent},
  {path:'product/update/:id', component: ProductAddComponent },
];


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    UserListComponent,
    ProductAddComponent,
    ProductListComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    FormsModule,
    RouterModule.forRoot(routes),
    ToastrModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
