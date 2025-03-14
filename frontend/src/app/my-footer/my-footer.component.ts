import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-my-footer',
  standalone: false,
  templateUrl: './my-footer.component.html',
  styleUrl: './my-footer.component.css'
})
export class MyFooterComponent implements OnInit {
  currentYear: number = new Date().getFullYear();

  constructor() { }

  ngOnInit(): void {
  }

}