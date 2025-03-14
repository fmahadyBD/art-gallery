import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyFooterComponent } from './my-footer.component';

describe('MyFooterComponent', () => {
  let component: MyFooterComponent;
  let fixture: ComponentFixture<MyFooterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MyFooterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyFooterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
