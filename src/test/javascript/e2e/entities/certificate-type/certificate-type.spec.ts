import { browser } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CertificateTypeComponentsPage, CertificateTypeDeleteDialog, CertificateTypeUpdatePage } from './certificate-type.page-object';

const expect = chai.expect;

describe('CertificateType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let certificateTypeComponentsPage: CertificateTypeComponentsPage;
  let certificateTypeUpdatePage: CertificateTypeUpdatePage;
  let certificateTypeDeleteDialog: CertificateTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
  });

  it('should load CertificateTypes', async () => {
    await navBarPage.goToEntity('certificate-type');
    certificateTypeComponentsPage = new CertificateTypeComponentsPage();
    expect(await certificateTypeComponentsPage.getTitle()).to.eq('compositekeyApp.certificateType.home.title');
  });

  it('should load create CertificateType page', async () => {
    await certificateTypeComponentsPage.clickOnCreateButton();
    certificateTypeUpdatePage = new CertificateTypeUpdatePage();
    expect(await certificateTypeUpdatePage.getPageTitle()).to.eq('compositekeyApp.certificateType.home.createOrEditLabel');
    await certificateTypeUpdatePage.cancel();
  });

  it('should create and save CertificateTypes', async () => {
    const nbButtonsBeforeCreate = await certificateTypeComponentsPage.countDeleteButtons();

    await certificateTypeComponentsPage.clickOnCreateButton();

    await certificateTypeUpdatePage.setNameInput('name');

    expect(await certificateTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');

    await certificateTypeUpdatePage.save();
    expect(await certificateTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await certificateTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CertificateType', async () => {
    const nbButtonsBeforeDelete = await certificateTypeComponentsPage.countDeleteButtons();
    await certificateTypeComponentsPage.clickOnLastDeleteButton();

    certificateTypeDeleteDialog = new CertificateTypeDeleteDialog();
    await certificateTypeDeleteDialog.clickOnConfirmButton();

    expect(await certificateTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
