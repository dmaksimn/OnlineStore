import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {NavigationComponent} from './parts/navigation/navigation.component';
import {CardComponent} from './pages/card/card.component';
import {PaginationComponent} from './parts/pagination/pagination.component';
import {AppRoutingModule} from './app-routing.module';
import {LoginComponent} from './pages/login/login.component';
import {SignupComponent} from './pages/signup/signup.component';
import {DetailComponent} from './pages/product-detail/detail.component';
import {FormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {CartComponent} from './pages/cart/cart.component';
import {CookieService} from "ngx-cookie-service";
import {ErrorInterceptor} from "./_interceptors/error-interceptor.service";
import {JwtInterceptor} from "./_interceptors/jwt-interceptor.service";
import {OrderComponent} from './pages/order/order.component';
import {OrderDetailComponent} from './pages/order-detail/order-detail.component';
import {ProductListComponent} from './pages/product-list/product.list.component';
import {UserDetailComponent} from './pages/user-edit/user-detail.component';
import {ProductEditComponent} from './pages/product-edit/product-edit.component';
// import { SocialLoginModule, SocialAuthServiceConfig } from 'angularx-social-login';
// import {
//   GoogleLoginProvider,
//   FacebookLoginProvider,
//   AmazonLoginProvider,
//   VKLoginProvider,
//   MicrosoftLoginProvider
// } from 'angularx-social-login';

@NgModule({
    declarations: [
        AppComponent,
        NavigationComponent,
        CardComponent,
        PaginationComponent,
        LoginComponent,
        SignupComponent,
        DetailComponent,
        CartComponent,
        OrderComponent,
        OrderDetailComponent,
        ProductListComponent,
        UserDetailComponent,
        ProductEditComponent,

    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        // SocialLoginModule,

    ],
    providers: [CookieService,
        {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
        {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
      // {provide: 'SocialAuthServiceConfig',
      //   useValue: {
      //     autoLogin: true,
      //     providers: [
      //       {
      //         id: GoogleLoginProvider.PROVIDER_ID,
      //         provider: new GoogleLoginProvider(
      //           '624796833023-clhjgupm0pu6vgga7k5i5bsfp6qp6egh.apps.googleusercontent.com'
      //         ),
      //       },
      //       {
      //         id: FacebookLoginProvider.PROVIDER_ID,
      //         provider: new FacebookLoginProvider('561602290896109'),
      //       },
      //       {
      //         id: AmazonLoginProvider.PROVIDER_ID,
      //         provider: new AmazonLoginProvider(
      //           'amzn1.application-oa2-client.f074ae67c0a146b6902cc0c4a3297935'
      //         ),
      //       },
      //       {
      //         id: VKLoginProvider.PROVIDER_ID,
      //         provider: new VKLoginProvider(
      //           '7624815'
      //         ),
      //       },
      //       {
      //         id: MicrosoftLoginProvider.PROVIDER_ID,
      //         provider: new MicrosoftLoginProvider('0611ccc3-9521-45b6-b432-039852002705'),
      //       }
      //     ],
      //   } as SocialAuthServiceConfig,
      // },
],
    bootstrap: [AppComponent]
})
export class AppModule {
}
