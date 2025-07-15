package dev.hiwa.iticket.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import dev.hiwa.iticket.domain.dto.response.QrCodeResponse;
import dev.hiwa.iticket.domain.entities.QrCode;
import dev.hiwa.iticket.domain.entities.Ticket;
import dev.hiwa.iticket.domain.enums.QrCodeStatus;
import dev.hiwa.iticket.exceptions.QrCodeGenerationException;
import dev.hiwa.iticket.mappers.QrCodeMapper;
import dev.hiwa.iticket.repository.QrCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class QrCodeService {

    private static final int QR_HEIGHT = 300;
    private static final int QR_WIDTH = 300;

    private final QrCodeRepository qrCodeRepository;
    public final QrCodeMapper qrCodeMapper;

    private final QRCodeWriter qrCodeWriter;

    public QrCodeResponse generateQrCodeFor(Ticket ticket) {
        try {
            UUID qrId = UUID.randomUUID();
            String qrCoedImage = _generateQrCoedImage(qrId);

            var qrCode = new QrCode();
            qrCode.setId(qrId);
            qrCode.setValue(qrCoedImage);
            qrCode.setStatus(QrCodeStatus.ACTIVE);
            qrCode.setTicket(ticket);

            QrCode savedQrCode = qrCodeRepository.saveAndFlush(qrCode);

            return qrCodeMapper.toQrCodeResponse(savedQrCode);

        } catch (WriterException | IOException ex) {
            throw new QrCodeGenerationException(ex.getMessage());
        }
    }

    private String _generateQrCoedImage(UUID uuid) throws WriterException, IOException {
        BitMatrix bitMatrix =
                qrCodeWriter.encode(uuid.toString(), BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);

        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }

}
