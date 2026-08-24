package backend.spring_ai.infrastructure.http;

import backend.spring_ai.application.DeleteTransactionUseCase;
import backend.spring_ai.application.ListTransactionsByCategoryUseCase;
import backend.spring_ai.application.ListTransactionsUseCase;
import backend.spring_ai.application.PersistTransactionUseCase;
import backend.spring_ai.application.UpdateTransactionUseCase;
import backend.spring_ai.domain.Category;
import backend.spring_ai.domain.TransactionId;
import backend.spring_ai.infrastructure.http.dto.request.TransactionRequest;
import backend.spring_ai.infrastructure.http.dto.response.TransactionResponse;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final PersistTransactionUseCase persistTransactionUseCase;
    private final ListTransactionsByCategoryUseCase listTransactionsByCategoryUseCase;
    private final ListTransactionsUseCase listTransactionsUseCase;
    private final UpdateTransactionUseCase updateTransactionUseCase;
    private final DeleteTransactionUseCase deleteTransactionUseCase;

    private final TranscriptionModel transcriptionModel;
    private final ChatClient chatClient;
    private final TextToSpeechModel textToSpeechModel;

    public TransactionController(PersistTransactionUseCase persistTransactionUseCase,
                                 ListTransactionsByCategoryUseCase listTransactionsByCategoryUseCase,
                                 ListTransactionsUseCase listTransactionsUseCase,
                                 UpdateTransactionUseCase updateTransactionUseCase,
                                 DeleteTransactionUseCase deleteTransactionUseCase,
                                 TranscriptionModel transcriptionModel,
                                 ChatClient chatClient, TextToSpeechModel textToSpeechModel) {
        this.persistTransactionUseCase = persistTransactionUseCase;
        this.listTransactionsByCategoryUseCase = listTransactionsByCategoryUseCase;
        this.listTransactionsUseCase = listTransactionsUseCase;
        this.updateTransactionUseCase = updateTransactionUseCase;
        this.deleteTransactionUseCase = deleteTransactionUseCase;
        this.transcriptionModel = transcriptionModel;
        this.chatClient = chatClient;
        this.textToSpeechModel = textToSpeechModel;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@RequestBody TransactionRequest request) {
        var transaction = persistTransactionUseCase.execute(request.toInput());
        return TransactionResponse.from(transaction);
    }

    @GetMapping("/category")
    public List<TransactionResponse> readTransactions(@RequestParam Category category) {
        return listTransactionsByCategoryUseCase.execute(category)
                .stream().map(TransactionResponse::from).toList();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse updateTransaction(@RequestParam UUID id, @RequestBody TransactionRequest request) {
        var transaction = updateTransactionUseCase.execute(new TransactionId(id), request.toInput());
        return TransactionResponse.from(transaction);
    }

    @GetMapping
    public List<TransactionResponse> listTransactions() {
        return listTransactionsUseCase.execute()
                .stream().map(TransactionResponse::from).toList();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(@RequestParam UUID id) {
        deleteTransactionUseCase.execute(new TransactionId(id));
    }

    @PostMapping(value = "/ai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "audio/mp3")
    public ResponseEntity<ByteArrayResource> transcribe(@RequestParam("file") MultipartFile file) {
        var transcription = transcriptionModel.transcribe(file.getResource());
        var responseText = chatClient.prompt().user(transcription).call().content();
        byte[] audio = textToSpeechModel.call(responseText);
        var resource = new ByteArrayResource(audio);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename("audio.mp3").build().toString())
                .body(resource);
    }
}
