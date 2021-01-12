import { browser } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { WithIdStringComponentsPage, WithIdStringDeleteDialog, WithIdStringUpdatePage } from './with-id-string.page-object';

const expect = chai.expect;

describe('WithIdString e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let withIdStringComponentsPage: WithIdStringComponentsPage;
  let withIdStringUpdatePage: WithIdStringUpdatePage;
  let withIdStringDeleteDialog: WithIdStringDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
  });

  it('should load WithIdStrings', async () => {
    await navBarPage.goToEntity('with-id-string');
    withIdStringComponentsPage = new WithIdStringComponentsPage();
    expect(await withIdStringComponentsPage.getTitle()).to.eq('compositekeyApp.withIdString.home.title');
  });

  it('should load create WithIdString page', async () => {
    await withIdStringComponentsPage.clickOnCreateButton();
    withIdStringUpdatePage = new WithIdStringUpdatePage();
    expect(await withIdStringUpdatePage.getPageTitle()).to.eq('compositekeyApp.withIdString.home.createOrEditLabel');
    await withIdStringUpdatePage.cancel();
  });

  it('should create and save WithIdStrings', async () => {
    const nbButtonsBeforeCreate = await withIdStringComponentsPage.countDeleteButtons();

    await withIdStringComponentsPage.clickOnCreateButton();

    await withIdStringUpdatePage.setIdInput('id');

    expect(await withIdStringUpdatePage.getIdInput()).to.eq('id', 'Expected Id value to be equals to id');

    await withIdStringUpdatePage.save();
    expect(await withIdStringUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await withIdStringComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last WithIdString', async () => {
    const nbButtonsBeforeDelete = await withIdStringComponentsPage.countDeleteButtons();
    await withIdStringComponentsPage.clickOnLastDeleteButton();

    withIdStringDeleteDialog = new WithIdStringDeleteDialog();
    await withIdStringDeleteDialog.clickOnConfirmButton();

    expect(await withIdStringComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
