package fr.tas.tasclientmobile;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import fr.tas.esipe.tasclientmobile.activity.PdfAllBillsActivity;
import fr.tas.esipe.tasclientmobile.endpoint.ConnectToRestApi;

/**
 *
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class PdfAllBillsActivityTest {

    private ConnectToRestApi connectToRestApi;

    private PdfAllBillsActivity pdfAllBillsActivity;

    @Before
    public void setUp(){
         connectToRestApi = Mockito.mock(ConnectToRestApi.class);

    }
    @Test
    public void shouldInitListOfBills() {
       /* when(connectToRestApi.execute())
                .thenReturn(i);*/
        pdfAllBillsActivity.initList();
    }

    @Test
    public void shouldDonwloadClientBill() {
        pdfAllBillsActivity.initViews();
    }
}
