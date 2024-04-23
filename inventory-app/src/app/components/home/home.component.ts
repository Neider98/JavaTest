import { Component } from '@angular/core';
import { User } from 'src/app/common/user';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent{
  
  users: User[] = [];
  selectedUser: boolean = false;
  user!: User;

  constructor() {}

  onUserSelected(isSelected: boolean) {
    this.selectedUser = isSelected;
  }
}
